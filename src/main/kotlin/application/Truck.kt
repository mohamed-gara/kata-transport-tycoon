package application

data class Truck(
  var id: String,
  var remaningHoursToDeliver: Int = 0,
  var remaningHoursToGoHome: Int = 0,
  var targetIsPort: Boolean = false,
) {

  fun move() {
    if (remaningHoursToDeliver > 0) remaningHoursToDeliver--
    else if (remaningHoursToGoHome > 0) remaningHoursToGoHome--
  }

  fun tryTakeContainer(container: Char): Boolean {
    if (isDriving()) return false
    return startDriveTo(container)
  }

  private fun isDriving() = remaningHoursToDeliver > 0 || remaningHoursToGoHome > 0

  private fun startDriveTo(container: Char): Boolean {
    val destinations = mapOf(
      'A' to 1,
      'B' to 5,
    )
    val duration: Int = destinations[container] ?: 0

    remaningHoursToDeliver = duration
    remaningHoursToGoHome = duration
    targetIsPort = duration == 1

    return duration > 0
  }

  fun hasNoDeliveryInProgress(): Boolean {
    return remaningHoursToDeliver == 0
  }

  fun isAtPort(): Boolean {
    return targetIsPort && remaningHoursToDeliver == 0 && remaningHoursToGoHome == 1
  }
}
