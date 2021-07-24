package application.simulator

data class TimeSimulator(
  private var hour: Int = 0,
  private val logEnabled: Boolean = true
) {

  lateinit var currentState: State

  fun calculateEndHour(
    initialState: State,
    eachHour: (State, hour: Int) -> State,
    stopWhen: (State) -> Boolean
  ): Int {

    currentState = initialState

    while (true) {
      logMessage("state at the beginning of $hour: $currentState")

      if (stopWhen(currentState)) {
        logMessage("Stop at hour: $hour")
        return hour
      }

      currentState = eachHour(currentState, hour)

      advanceTimeToNextHour()

      if (hour == 100) throw RuntimeException("Too much iterations...")
    }
  }

  private fun advanceTimeToNextHour() {
    hour++
  }

  private fun logMessage(msg: String) {
    if (logEnabled) println(msg)
  }
}
