package application.simulator

import application.carriers.*
import application.map.*


data class State(
  val factory: Factory,
  val port: Port = Port(),
  val trucks: Set<Carrier> = setOf(Carrier("truck_1"), Carrier("truck_2")),
  val ship: Carrier = Carrier("ship"),
  val nextTransportId: Int = 0,
  val events: List<TransportEvent> = listOf(),
) {

  private val carriers: List<Carrier>
    get() = listOf(trucks, listOf(ship)).flatten()

  private val containersArrivedToPort: List<Container>
    get() = trucksArrivedToPort.map { it.currentItinerary.container }

  private val trucksArrivedToPort: List<Carrier>
    get() = trucks.filter { it.isAtDestination() && it.currentItinerary.container.destination == "A" }

  private val trucksArrivedToB: List<Carrier>
    get() = trucks.filter { it.isAtDestination() && it.currentItinerary.container.destination == "B" }

  fun allContainersAreDelivered(): Boolean =
    factory.hasNoContainerToDeliver()
        && port.hasNoContainerToDeliver()
        && carriers.noneHasDeliveryInProgress()

  fun calculateNextState(hour: Int): State =
    loadContainersInTrucksAtFactory()
      .loadContainerInShipAtPort(hour)
      .moveCarriers()
      .unloadTrucksAtPort(hour)
      .unloadTrucksAtB(hour)
      .finishTruckItinerary(hour)

  private fun loadContainersInTrucksAtFactory(): State =
    trucks.filter { it.isAtDeparture() }
      .fold(this, ::nextState)

  private fun loadContainerInShipAtPort(hour: Int): State =
    if (ship.isAtDeparture())
      port.nextContainerToDeliver()
        .map { container -> shipItineraryFor(this.nextTransportId, container) }
        .map { itinerary -> ship.carryContainer(itinerary) }
        .map { updatedShip ->
          copy(
            ship = updatedShip,
            port = port.peekNextContainerToDeliver(),
            events = events + shipDepartFromPortEvent(hour, updatedShip)
          )
        }
        .orElse(this)
    else this

  private fun moveCarriers(): State = copy(
    trucks = trucks.moveAll(),
    ship = ship.move(),
  )

  private fun unloadTrucksAtPort(hour: Int): State =
    copy(
      port = port.putInWarehouse(containersArrivedToPort),
      events = events + trucksArrivedToPort.flatMap { truck ->
        listOf(
          truckPortArriveEvent(hour, truck),
          truckDepartFromPortToFactoryEvent(hour, truck),
        )
      },
    )

  private fun unloadTrucksAtB(hour: Int): State =
    copy(
      events = events + trucksArrivedToB.flatMap { truck ->
        listOf(
          truckArriveToBEvent(hour, truck),
          truckDepartFromBToFactoryEvent(hour, truck),
        )
      },
    )

  private fun finishTruckItinerary(hour: Int): State =
    copy(
      trucks = trucks.map { if (it.isAtDeparture() && it.currentItinerary !== NO_ITINERARY) it.copy(currentItinerary= NO_ITINERARY) else it}.toSet(),
      events = events +
          trucks.filter { it.isAtDeparture() && it.currentItinerary !== NO_ITINERARY }
                .map { truck -> truckArriveToFactoryEvent(hour, truck) },
    )
}

private fun nextState(state: State, truck: Carrier): State =
  state.factory.nextContainerToDeliver()
    .map { container -> truckItineraryFor(state.nextTransportId, container) }
    .map { itinerary -> truck.copy(currentItinerary = itinerary) }
    .map { updatedTruck ->
      state.copy(
        trucks = setOf(updatedTruck).plus(state.trucks), // TODO remove plus
        factory = state.factory.peekNextContainersToDeliver(),
        events = state.events + truckDepartEvent(state, updatedTruck),
        nextTransportId = state.nextTransportId + 1,
      )
    }
    .orElse(state)

private fun truckDepartEvent(
  state: State,
  truck: Carrier
) = TransportEvent(
  "",
  "DEPART",
  0,
  state.nextTransportId,
  "TRUCK",
  "FACTORY",
  truck.currentItinerary.destination,
  listOf(
    Cargo(
      state.nextTransportId.toString(),
      truck.currentItinerary.container.destination,
      "FACTORY"
    )
  )
)

private fun truckPortArriveEvent(
  hour: Int,
  truck: Carrier
) = TransportEvent(
  "",
  "ARRIVE",
  hour+1,
  truck.currentItinerary.transportId,
  "TRUCK",
  "PORT",
  "",
  listOf(
    Cargo(
      truck.currentItinerary.transportId.toString(),
      truck.currentItinerary.container.destination,
      "FACTORY"
    )
  )
)

private fun truckDepartFromPortToFactoryEvent(
  hour: Int,
  truck: Carrier,
) = TransportEvent(
  "",
  "DEPART",
  hour+1,
  truck.currentItinerary.transportId,
  "TRUCK",
  "PORT",
  "FACTORY",
  listOf()
)

private fun shipDepartFromPortEvent(
  hour: Int,
  ship: Carrier,
) = TransportEvent(
  "",
  "DEPART",
  hour,
  ship.currentItinerary.transportId,
  "SHIP",
  "PORT",
  "A",
  listOf(
    Cargo(
      ship.currentItinerary.container.id,
      ship.currentItinerary.container.destination,
      "FACTORY"
    )
  )
)

private fun truckArriveToFactoryEvent(
  hour: Int,
  truck: Carrier
) = TransportEvent(
  "",
  "ARRIVE",
  hour + 1,
  truck.currentItinerary.transportId,
  "TRUCK",
  "FACTORY",
  "",
  listOf()
)

private fun truckArriveToBEvent(
  hour: Int,
  truck: Carrier
) = TransportEvent(
  "",
  "ARRIVE",
  hour + 1,
  truck.currentItinerary.transportId,
  "TRUCK",
  "B",
  "",
  listOf(
    Cargo(
      truck.currentItinerary.container.id,
      truck.currentItinerary.container.destination,
      "FACTORY"
    )
  )
)

private fun truckDepartFromBToFactoryEvent(
  hour: Int,
  truck: Carrier,
) = TransportEvent(
  "",
  "DEPART",
  hour+1,
  truck.currentItinerary.transportId,
  "TRUCK",
  "B",
  "FACTORY",
  listOf()
)
