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

  private fun tryTakingNextContainerByTruck1(): State {
    if (factory.hasNoContainerToDeliver()) return this

    val trip = truckTripFor(factory.nextContainerToDeliver())
    val newTruck1 = truck1.tryCarryContainer(trip)
    if (newTruck1 !== truck1) {
      return copy(truck1 = newTruck1, factory = factory.peekNextContainerToDeliver())
    }
    return this
  }

  private fun tryTakingNextContainerByTruck2(): State {
    if (factory.hasNoContainerToDeliver()) return this

    val trip = truckTripFor(factory.containersAtFactory.first())
    val newTruck2 = truck2.tryCarryContainer(trip)
    if (newTruck2 !== truck2) {
      return copy(truck2 = newTruck2, factory = factory.peekNextContainerToDeliver())
    }
    return this
  }

  private fun truckTripFor(destination: Char): Trip {
    val destinations = mapOf(
      'A' to 1,
      'B' to 5,
    )
    val duration: Int = destinations[destination] ?: 0
    return Trip(duration)
  }

  private fun tryTakingNextContainerByShip(): State {
    if (port.containersAtPort == 0) return this
    val newShip = ship.tryCarryContainer(Trip(4))
    if (newShip !== ship) {
      return copy(ship = newShip, port = port.peekNextContainerToDeliver())
    }
    return this
  }

  private fun unloadTrucksAtPort(): State =
    copy(port = port.putInWarehouse(numberOfTrucksArrivedToPort))

  private fun moveHandlers(): State = copy(
    truck1 = truck1.move(),
    truck2 = truck2.move(),
    ship = ship.move(),
  )
}
