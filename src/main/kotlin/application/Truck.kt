package application

data class Truck(
  var id: String,
  var remaningHoursToDeliver: Int = 0,
  var remaningHoursToGoHome: Int = 0
) {

  fun isDriving() = remaningHoursToDeliver > 0 || remaningHoursToGoHome > 0

  fun startDrive() {
    remaningHoursToDeliver = 5
    remaningHoursToGoHome = 5
  }

  fun move() {
    if (remaningHoursToDeliver > 0) remaningHoursToDeliver--
    else if (remaningHoursToGoHome > 0) remaningHoursToGoHome--
  }

  fun tryTakeContainer(): Boolean {
    if (isDriving()) return false
    startDrive()
    return true
  }

  fun hasNoDeliveryInProgress(): Boolean {
    return remaningHoursToDeliver == 0
  }
}
