package application

data class State(
  val remainingContainerForTrucks: String,
  private val remainingContainerForShip: Int = 0,
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
    remainingContainerForTrucks.isEmpty()
        && remainingContainerForShip == 0
        && containerHandlers.all { it.hasNoDeliveryInProgress() }

  fun calculateNextState(): State =
    tryTakingNextContainerByTruck1()
      .tryTakingNextContainerByTruck2()
      .tryTakingNextContainerByShip()
      .moveHandlers()
      .unloadTrucksAtPort()

  private fun tryTakingNextContainerByTruck1(): State {
    if (remainingContainerForTrucks.isBlank()) return this
    val newTruck1 = truck1.tryTakeContainer(remainingContainerForTrucks.first())
    if (newTruck1 !== truck1) {
      return copy(truck1 = newTruck1, remainingContainerForTrucks = remainingContainerForTrucks.drop(1))
    }
    return this
  }

  private fun tryTakingNextContainerByTruck2(): State {
    if (remainingContainerForTrucks.isBlank()) return this
    val newTruck2 = truck2.tryTakeContainer(remainingContainerForTrucks.first())
    if (newTruck2 !== truck2) {
      return copy(truck2 = newTruck2, remainingContainerForTrucks = remainingContainerForTrucks.drop(1))
    }
    return this
  }

  private fun tryTakingNextContainerByShip(): State {
    if (remainingContainerForShip == 0) return this
    val newShip = ship.tryTakeContainer('A')
    if (newShip !== ship) {
      return copy(ship = newShip, remainingContainerForShip = remainingContainerForShip-1)
    }
    return this
  }

  private fun unloadTrucksAtPort(): State =
    copy(remainingContainerForShip = remainingContainerForShip + numberOfTrucksArrivedToPort)

  private fun moveHandlers(): State = copy(
    truck1 = truck1.move(),
    truck2 = truck2.move(),
    ship = ship.move(),
  )
}
