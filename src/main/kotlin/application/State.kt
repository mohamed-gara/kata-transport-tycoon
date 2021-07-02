package application

import application.carriers.Carrier
import application.carriers.Ship
import application.carriers.Truck
import application.map.Factory
import application.map.Port
import application.map.Itinerary
import application.map.truckItineraryFor


data class State(
  private val factory: Factory,
  private val port: Port = Port(),
  private val truck1: Truck = Truck("1"),
  private val truck2: Truck = Truck("2"),
  private val ship: Ship = Ship(),
) {

  private val containerHandlers: List<Carrier>
    get() = listOf(truck1, truck2, ship)

  private val trucks: List<Truck>
    get() = listOf(truck1, truck2)

  private val numberOfTrucksArrivedToPort: Int
    get() = trucks.count { it.isAtPort() }

  fun allContainersAreDelivered(): Boolean =
    factory.hasNoContainerToDeliver()
        && port.hasNoContainerToDeliver()
        && containerHandlers.none { it.hasDeliveryInProgress() }

  fun calculateNextState(): State =
    tryTakingNextContainerByTruck1()
      .tryTakingNextContainerByTruck2()
      .tryTakingNextContainerByShip()
      .moveHandlers()
      .unloadTrucksAtPort()

  private fun tryTakingNextContainerByTruck1(): State =
    factory.nextContainerToDeliver()
      .map { truckItineraryFor(it) }
      .filter { itinerary -> truck1.canCarryContainer(itinerary) }
      .map { itinerary -> copy(truck1 = truck1.carryContainer(itinerary), factory = factory.peekNextContainerToDeliver()) }
      .orElse(this)

  private fun tryTakingNextContainerByTruck2(): State =
    factory.nextContainerToDeliver()
      .map { truckItineraryFor(it) }
      .filter { itinerary -> truck2.canCarryContainer(itinerary) }
      .map { itinerary -> copy(truck2 = truck2.carryContainer(itinerary), factory = factory.peekNextContainerToDeliver()) }
      .orElse(this)

  private fun tryTakingNextContainerByShip(): State =
    port.nextContainerToDeliver()
      .map { Itinerary(4) }
      .filter { itinerary -> ship.canCarryContainer(itinerary) }
      .map { itinerary -> copy(ship = ship.carryContainer(itinerary), port = port.peekNextContainerToDeliver()) }
      .orElse(this)

  private fun unloadTrucksAtPort(): State =
    copy(port = port.putInWarehouse(numberOfTrucksArrivedToPort))

  private fun moveHandlers(): State = copy(
    truck1 = truck1.move(),
    truck2 = truck2.move(),
    ship = ship.move(),
  )
}
