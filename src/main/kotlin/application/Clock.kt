package application

data class Clock(
  var hour: Int = 0,
) {
  fun advanceToNextHour() {
    hour++
  }
}
