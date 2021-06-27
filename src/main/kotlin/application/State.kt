package application

data class State(
  val remainingContainerForTrucks: String = "",
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
    tryTakingNextContainerByTruck(truck1)
      .tryTakingNextContainerByTruck(truck2)
      .tryTakingNextContainerByShip(ship)
      .moveHandlers()
      .unloadTrucksAtPort()

  private fun tryTakingNextContainerByTruck(truck: ContainerHandler): State {
    if (remainingContainerForTrucks.isBlank()) return this
    if (truck.tryTakeContainer(remainingContainerForTrucks.first())) {
      return copy(remainingContainerForTrucks = remainingContainerForTrucks.drop(1))
    }
    return this
  }

  private fun tryTakingNextContainerByShip(ship: ContainerHandler): State {
    if (remainingContainerForShip == 0) return this
    if (ship.tryTakeContainer('A')) {
      return copy(remainingContainerForShip = remainingContainerForShip-1)
    }
    return this
  }

  private fun unloadTrucksAtPort(): State =
    copy(remainingContainerForShip = remainingContainerForShip + numberOfTrucksArrivedToPort)

  private fun moveHandlers(): State {
    containerHandlers.forEach { it.move() }
    return this
  }
}
