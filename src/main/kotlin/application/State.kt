package application

data class State(
  private val factory: Factory,
  private val containersAtPort: Int = 0,
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
        && containersAtPort == 0
        && containerHandlers.none { it.hasDeliveryInProgress() }

  fun calculateNextState(): State =
    tryTakingNextContainerByTruck1()
      .tryTakingNextContainerByTruck2()
      .tryTakingNextContainerByShip()
      .moveHandlers()
      .unloadTrucksAtPort()

  private fun tryTakingNextContainerByTruck1(): State {
    if (factory.hasNoContainerToDeliver()) return this

    val trip = trunckTripFor(factory.nextContainerToDeliver())
    val newTruck1 = truck1.tryCarryContainer(trip)
    if (newTruck1 !== truck1) {
      return copy(truck1 = newTruck1, factory= factory.copy(containersAtFactory = factory.peekNextContainerToDeliver()))
    }
    return this
  }

  private fun tryTakingNextContainerByTruck2(): State {
    if (factory.hasNoContainerToDeliver()) return this

    val trip = trunckTripFor(factory.containersAtFactory.first())
    val newTruck2 = truck2.tryCarryContainer(trip)
    if (newTruck2 !== truck2) {
      return copy(truck2 = newTruck2, factory= factory.copy(containersAtFactory = factory.peekNextContainerToDeliver()))
    }
    return this
  }

  private fun trunckTripFor(destination: Char): Trip {
    val destinations = mapOf(
      'A' to 1,
      'B' to 5,
    )
    val duration: Int = destinations[destination] ?: 0
    return Trip(duration)
  }

  private fun tryTakingNextContainerByShip(): State {
    if (containersAtPort == 0) return this
    val newShip = ship.tryCarryContainer(Trip(4))
    if (newShip !== ship) {
      return copy(ship = newShip, containersAtPort = containersAtPort-1)
    }
    return this
  }

  private fun unloadTrucksAtPort(): State =
    copy(containersAtPort = containersAtPort + numberOfTrucksArrivedToPort)

  private fun moveHandlers(): State = copy(
    truck1 = truck1.move(),
    truck2 = truck2.move(),
    ship = ship.move(),
  )
}
