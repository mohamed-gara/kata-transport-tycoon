package application

data class Truck(
  var id: String,
  var remaningHoursToDeliver: Int = 0,
  var remaningHoursToGoHome: Int = 0
) {

  fun isDriving() = remaningHoursToDeliver > 0 || remaningHoursToGoHome > 0

  fun startDrive(container: Char) {
    val destinations = mapOf('B' to 5)
    val duration: Int = destinations[container] ?: 0

    remaningHoursToDeliver = duration
    remaningHoursToGoHome = duration
  }

  fun move() {
    if (remaningHoursToDeliver > 0) remaningHoursToDeliver--
    else if (remaningHoursToGoHome > 0) remaningHoursToGoHome--
  }

  fun tryTakeContainer(container: Char): Boolean {
    if (isDriving()) return false
    startDrive(container)
    return true
  }

  fun hasNoDeliveryInProgress(): Boolean {
    return remaningHoursToDeliver == 0
  }
}
