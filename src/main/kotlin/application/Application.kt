package application

class Application {

  var hour = 0

  var remainingContainer = ""

  var truck1RemaningHoursToDeliver = 0
  var truck2RemaningHoursToDeliver = 0

  var truck1RemaningHoursToGoHome = 0
  var truck2RemaningHoursToGoHome = 0

  fun transport(containers: String): Int {
    if (containers.isBlank()) return 0

    remainingContainer = containers

    while (true) {
      takeNextContainerByTruck1()
      takeNextContainerByTruck2()

      hour++

      moveTruck1()
      moveTruck2()

      if (remainingContainer.isEmpty() && truck1RemaningHoursToDeliver == 0 && truck2RemaningHoursToDeliver == 0) return hour
    }
  }

  private fun moveTruck1() {
    if (truck1RemaningHoursToDeliver > 0) truck1RemaningHoursToDeliver--
    else if (truck1RemaningHoursToGoHome > 0) truck1RemaningHoursToGoHome--
  }

  private fun moveTruck2() {
    if (truck2RemaningHoursToDeliver > 0) truck2RemaningHoursToDeliver--
    else if (truck2RemaningHoursToGoHome > 0) truck2RemaningHoursToGoHome--
  }

  private fun takeNextContainerByTruck1() {
    if (truck1RemaningHoursToDeliver > 0 || truck1RemaningHoursToGoHome > 0) return
    remainingContainer = remainingContainer.drop(1)
    truck1RemaningHoursToDeliver = 5
    truck1RemaningHoursToGoHome = 5
  }

  private fun takeNextContainerByTruck2() {
    if (truck2RemaningHoursToDeliver > 0 || truck2RemaningHoursToGoHome > 0) return

    remainingContainer = remainingContainer.drop(1)
    truck2RemaningHoursToDeliver = 5
    truck2RemaningHoursToGoHome = 5
  }
}
