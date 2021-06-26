package application

class Application {

  val truck1 = Truck("1")
  val truck2 = Truck("2")

  var remainingContainer = ""

  val clock = Clock()

  fun transport(containers: String): Int {
    remainingContainer = containers

    while (true) {
      if (allContainersAreDelivered()) return clock.hour

      tryTakingNextContainerBy(truck1)
      tryTakingNextContainerBy(truck2)

      truck1.move()
      truck2.move()

      clock.advanceToNextHour()
    }
  }

  private fun tryTakingNextContainerBy(truck: Truck) {
    if (remainingContainer.isBlank()) return
    if (truck.tryTakeContainer())
      remainingContainer = remainingContainer.drop(1)
  }

  private fun allContainersAreDelivered() =
    remainingContainer.isEmpty() && truck1.remaningHoursToDeliver == 0 && truck2.remaningHoursToDeliver == 0
}
