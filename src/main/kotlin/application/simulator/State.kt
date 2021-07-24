package application.simulator

import application.carriers.*
import application.map.*


data class State(
  val factory: Factory,
  val port: Port = Port(),
  val trucks: Set<Carrier> = setOf(Carrier("truck_1"), Carrier("truck_2")),
  val ship: Carrier = Carrier("ship"),
  val events: List<TransportEvent> = listOf(),
) {

  private val carriers: List<Carrier>
    get() = listOf(trucks, listOf(ship)).flatten()

  private val containersArrivedToPort: List<Container>
    get() = trucks.filter { it.isAtDestination() && it.currentItinerary.container.destination == "A" }
      .map { it.currentItinerary.container }

  fun allContainersAreDelivered(): Boolean =
    factory.hasNoContainerToDeliver()
        && port.hasNoContainerToDeliver()
        && carriers.noneHasDeliveryInProgress()

  fun calculateNextState(): State =
    loadContainersInTrucksAtFactory()
      .loadContainerInShipAtPort()
      .moveCarriers()
      .unloadTrucksAtPort()

  private fun loadContainersInTrucksAtFactory(): State =
    trucks.filter { it.isAtDeparture() }
      .fold(this, ::nextState)

  private fun loadContainerInShipAtPort(): State =
    if (ship.isAtDeparture())
      port.nextContainerToDeliver()
        .map { container -> shipItineraryFor(container) }
        .map { itinerary -> copy(ship = ship.carryContainer(itinerary), port = port.peekNextContainerToDeliver()) }
        .orElse(this)
    else this

  private fun moveCarriers(): State = copy(
    trucks = trucks.moveAll(),
    ship = ship.move(),
  )

  private fun unloadTrucksAtPort(): State =
    copy(port = port.putInWarehouse(containersArrivedToPort))
}

private fun nextState(state: State, truck: Carrier): State =
  state.factory.nextContainerToDeliver()
    .map { container -> truckItineraryFor(container) }
    .map { itinerary -> truck.copy(currentItinerary = itinerary) }
    .map { truck ->
      state.copy(
        trucks = setOf(truck).plus(state.trucks), // TODO remove plus
        factory = state.factory.peekNextContainersToDeliver(),
        events = state.events + TransportEvent("", "DEPART", 0, 0, "TRUCK", "FACTORY", "PORT", listOf(Cargo("0", "A", "FACTORY"))),
      )
    }
    .orElse(state)

