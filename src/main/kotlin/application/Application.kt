package application

class Application {

  val truck1 = Truck("1")
  val truck2 = Truck("2")

  var remainingContainer = ""

  val clock = Clock()

  fun transport(containers: String): Int {
    if (containers.isBlank()) return 0

    remainingContainer = containers

    while (true) {
      tryTakingNextContainerBy(truck1)
      tryTakingNextContainerBy(truck2)

      clock.advanceToNextHour()

      truck1.move()
      truck2.move()

      if (allContainersAreDelivered()) return clock.hour
    }
  }

  private fun tryTakingNextContainerBy(truck: Truck) {
    if (truck.tryTakeContainer())
      remainingContainer = remainingContainer.drop(1)
  }

  private fun allContainersAreDelivered() =
    remainingContainer.isEmpty() && truck1.remaningHoursToDeliver == 0 && truck2.remaningHoursToDeliver == 0
}
