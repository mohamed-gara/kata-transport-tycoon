package application

data class Simulateur(
  private var hour: Int = 0,
) {
  fun finishHourFor(
    eachHour: (Int) -> Unit,
    stopCondition: () -> Boolean
  ): Int {
    while (true) {
      if (stopCondition()) return hour

      eachHour(hour)

      advanceToNextHour()
    }
  }

  private fun advanceToNextHour() {
    hour++
  }
}
