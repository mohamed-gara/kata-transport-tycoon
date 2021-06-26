package application

class Application {

  var hour = 0

  val truck1 = Truck("1")
  val truck2 = Truck("2")

  var remainingContainer = ""

  fun transport(containers: String): Int {
    if (containers.isBlank()) return 0

    remainingContainer = containers

    while (true) {
      tryTakingNextContainerBy(truck1)
      tryTakingNextContainerBy(truck2)

      hour++

      truck1.move()
      truck2.move()

      if (allContainersAreDelivred()) return hour
    }
  }

  private fun tryTakingNextContainerBy(truck: Truck) {
    if (truck.tryTakeContainer())
      remainingContainer = remainingContainer.drop(1)
  }

  private fun allContainersAreDelivred() =
    remainingContainer.isEmpty() && truck1.remaningHoursToDeliver == 0 && truck2.remaningHoursToDeliver == 0
}
