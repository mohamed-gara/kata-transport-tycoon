package application

data class State(
  var remainingContainerForTrucks: String = "",
  var remainingContainerForShip: Int = 0,
  val truck1: Truck = Truck("1"),
  val truck2: Truck = Truck("2"),
  val ship: Ship = Ship(),
) {

  val containerHandlers: List<ContainerHandler>
    get() = listOf(truck1, truck2, ship)

  val trucks: List<Truck>
    get() = listOf(truck1, truck2)

  fun allContainersAreDelivered(): Boolean =
    remainingContainerForTrucks.isEmpty()
        && remainingContainerForShip == 0
        && containerHandlers.all { it.hasNoDeliveryInProgress() }

  fun calculateNextState(): State {
    val state = tryTakingNextContainerByTruck(truck1)
      .tryTakingNextContainerByTruck(truck2)
      .tryTakingNextContainerByShip(ship)

    containerHandlers.forEach { it.move() }

    trucks.forEach {
      if (it.isAtPort()) state.remainingContainerForShip++
    }

    return state
  }

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
}
