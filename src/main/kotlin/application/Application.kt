package application

class Application {

  val truck1 = Truck("1")
  val truck2 = Truck("2")

  var remainingContainer = ""

  val simulator = Simulateur()

  fun transport(containers: String): Int {
    remainingContainer = containers

    return simulator.finishHourFor(
      eachHour = {
        tryTakingNextContainerBy(truck1)
        tryTakingNextContainerBy(truck2)

        truck1.move()
        truck2.move()
      },
      stopCondition = ::allContainersAreDelivered
    )
  }

  private fun tryTakingNextContainerBy(truck: Truck) {
    if (remainingContainer.isBlank()) return
    if (truck.tryTakeContainer())
      remainingContainer = remainingContainer.drop(1)
  }

  private fun allContainersAreDelivered(): Boolean =
    remainingContainer.isEmpty() && truck1.hasNoDeliveryInProgress() && truck2.hasNoDeliveryInProgress()
}
