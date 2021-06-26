package application

class Application {

  var hour = 0
  var truck1RemaningHoursToDeliver = 0
  var truck2RemaningHoursToDeliver = 0

  fun transport(containers: String): Int {
    if (containers.isBlank()) return 0

    truck1RemaningHoursToDeliver = 5
    truck2RemaningHoursToDeliver = 5

    while (true) {
      hour++

      truck1RemaningHoursToDeliver--
      truck2RemaningHoursToDeliver--

      if (truck1RemaningHoursToDeliver==0 && truck2RemaningHoursToDeliver==0) return hour
    }
  }
}
