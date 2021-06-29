package application


data class State(
  private val factory: Factory,
  private val port: Port = Port(),
  private val truck1: Truck = Truck("1"),
  private val truck2: Truck = Truck("2"),
  private val ship: Ship = Ship(),
) {

  private val containerHandlers: List<ContainerHandler>
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
      .map { truckTripFor(it) }
      .filter { trip -> truck1.canCarryContainer(trip) }
      .map { trip -> copy(truck1 = truck1.carryContainer(trip), factory = factory.peekNextContainerToDeliver()) }
      .orElse(this)

  private fun tryTakingNextContainerByTruck2(): State =
    factory.nextContainerToDeliver()
      .map { truckTripFor(it) }
      .filter { trip -> truck2.canCarryContainer(trip) }
      .map { trip -> copy(truck2 = truck2.carryContainer(trip), factory = factory.peekNextContainerToDeliver()) }
      .orElse(this)

  private fun tryTakingNextContainerByShip(): State =
    port.nextContainerToDeliver()
      .map { Trip(4) }
      .filter { trip -> ship.canCarryContainer(trip) }
      .map { trip -> copy(ship = ship.carryContainer(trip), port = port.peekNextContainerToDeliver()) }
      .orElse(this)

  private fun unloadTrucksAtPort(): State =
    copy(port = port.putInWarehouse(numberOfTrucksArrivedToPort))

  private fun moveHandlers(): State = copy(
    truck1 = truck1.move(),
    truck2 = truck2.move(),
    ship = ship.move(),
  )
}
