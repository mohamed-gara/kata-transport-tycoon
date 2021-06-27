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
    tryTakingNextContainerByTruck(truck1)
    tryTakingNextContainerByTruck(truck2)
    tryTakingNextContainerByShip(ship)

    containerHandlers.forEach { it.move() }

    trucks.forEach {
      if (it.isAtPort()) remainingContainerForShip++
    }

    return this
  }

  private fun tryTakingNextContainerByTruck(truck: ContainerHandler) {
    if (remainingContainerForTrucks.isBlank()) return
    if (truck.tryTakeContainer(remainingContainerForTrucks.first())) {
      remainingContainerForTrucks = remainingContainerForTrucks.drop(1)
    }
  }

  private fun tryTakingNextContainerByShip(ship: ContainerHandler) {
    if (remainingContainerForShip == 0) return
    if (ship.tryTakeContainer('A')) {
      remainingContainerForShip--
    }
  }
}
