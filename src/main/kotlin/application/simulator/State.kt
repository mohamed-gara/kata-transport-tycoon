package application.simulator

import application.carriers.Carrier
import application.carriers.moveAll
import application.carriers.noneHasDeliveryInProgress
import application.map.Factory
import application.map.Port
import application.map.Itinerary
import application.map.truckItineraryFor


data class State(
  val factory: Factory,
  val port: Port = Port(),
  val trucks: Set<Carrier> = setOf(Carrier("truck_1"), Carrier("truck_2")),
  val ship: Carrier = Carrier("ship"),
) {

  private val carriers: List<Carrier>
    get() = listOf(trucks, listOf(ship)).flatten()

  private val numberOfTrucksArrivedToPort: Int
    get() = trucks.count { it.isAtDestination() && it.currentItinerary.duration == 1 } // TODO make destination explicit

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
        .map { Itinerary(4) }
        .map { itinerary -> copy(ship = ship.carryContainer(itinerary), port = port.peekNextContainerToDeliver()) }
        .orElse(this)
    else this

  private fun moveCarriers(): State = copy(
    trucks = trucks.moveAll(),
    ship = ship.move(),
  )

  private fun unloadTrucksAtPort(): State =
    copy(port = port.putInWarehouse(numberOfTrucksArrivedToPort))
}

private fun nextState(state: State, truck: Carrier): State =
  state.factory.nextContainerToDeliver()
    .map { destination -> truckItineraryFor(destination) }
    .map { itinerary -> truck.copy(currentItinerary = itinerary) }
    .map { truck ->
      state.copy(
        trucks = setOf(truck).plus(state.trucks),
        factory = state.factory.peekNextContainersToDeliver()
      )
    } // TODO remove plus
    .orElse(state)
