package application

data class Ship(
  val id: String = "",
  var remaningHoursToDeliver: Int = 0,
  var remaningHoursToGoHome: Int = 0
) {

  fun tryTakeContainer(): Boolean {
    if (isNavigating()) return false
    startNavigation()
    return true
  }

  private fun isNavigating(): Boolean =
    remaningHoursToDeliver > 0 || remaningHoursToGoHome > 0

  private fun startNavigation() {
    remaningHoursToDeliver = 4
    remaningHoursToGoHome = 4
  }

  fun move() {
    if (remaningHoursToDeliver > 0) remaningHoursToDeliver--
    else if (remaningHoursToGoHome > 0) remaningHoursToGoHome--
  }

  fun hasNoDeliveryInProgress(): Boolean {
    return remaningHoursToDeliver == 0
  }
}
