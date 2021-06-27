package application

class Application {

  val truck1 = Truck("1")
  val truck2 = Truck("2")
  val ship = Ship()

  var remainingContainerForTrucks = ""
  var remainingContainerForShip = 0

  val simulator = TimeSimulator()

  fun transport(containers: String): Int {
    remainingContainerForTrucks = containers

    return simulator.finishHourFor(
      eachHour = {
        tryTakingNextContainerBy(truck1)
        tryTakingNextContainerBy(truck2)
        tryTakingNextContainerBy(ship)

        truck1.move()
        truck2.move()
        ship.move()

        if (truck1.isAtPort()) remainingContainerForShip++
        if (truck2.isAtPort()) remainingContainerForShip++
      },
      stopCondition = ::allContainersAreDelivered
    )
  }

  private fun tryTakingNextContainerBy(truck: Truck) {
    if (remainingContainerForTrucks.isBlank()) return
    if (truck.tryTakeContainer(remainingContainerForTrucks.first()))
      remainingContainerForTrucks = remainingContainerForTrucks.drop(1)
  }

  private fun tryTakingNextContainerBy(ship: Ship) {
    if (remainingContainerForShip == 0) return
    if (ship.tryTakeContainer()) remainingContainerForShip--
  }

  private fun allContainersAreDelivered(): Boolean =
    remainingContainerForTrucks.isEmpty()
        && remainingContainerForShip == 0
        && truck1.hasNoDeliveryInProgress()
        && truck2.hasNoDeliveryInProgress()
        && ship.hasNoDeliveryInProgress()
}
