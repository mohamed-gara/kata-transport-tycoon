package application

data class State(
  private val containersAtFactory: String,
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
    containersAtFactory.isEmpty()
        && containersAtPort == 0
        && containerHandlers.all { it.hasNoDeliveryInProgress() }

  fun calculateNextState(): State =
    tryTakingNextContainerByTruck1()
      .tryTakingNextContainerByTruck2()
      .tryTakingNextContainerByShip()
      .moveHandlers()
      .unloadTrucksAtPort()

  private fun tryTakingNextContainerByTruck1(): State {
    if (containersAtFactory.isBlank()) return this
    val newTruck1 = truck1.tryCarryContainerTo(containersAtFactory.first())
    if (newTruck1 !== truck1) {
      return copy(truck1 = newTruck1, containersAtFactory = containersAtFactory.drop(1))
    }
    return this
  }

  private fun tryTakingNextContainerByTruck2(): State {
    if (containersAtFactory.isBlank()) return this
    val newTruck2 = truck2.tryCarryContainerTo(containersAtFactory.first())
    if (newTruck2 !== truck2) {
      return copy(truck2 = newTruck2, containersAtFactory = containersAtFactory.drop(1))
    }
    return this
  }

  private fun tryTakingNextContainerByShip(): State {
    if (containersAtPort == 0) return this
    val newShip = ship.tryCarryContainerTo('A')
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
