package application

data class TimeSimulator(
  private var hour: Int = 0,
) {

  fun finishHourFor(
    eachHour: () -> Unit,
    stopCondition: () -> Boolean
  ): Int {
    while (true) {
      if (stopCondition()) return hour

      eachHour()

      advanceToNextHour()
    }
  }

  private fun advanceToNextHour() {
    hour++
  }
}
