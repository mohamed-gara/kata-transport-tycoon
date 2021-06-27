package application

import java.lang.RuntimeException

data class TimeSimulator(
  private var hour: Int = 0,
  private val logEnabled: Boolean = false
) {

  fun finishHourFor(
    eachHour: (State) -> State,
    stopWhen: (State) -> Boolean,
    initialState: State
  ): Int {

    var currentState = initialState

    while (true) {
      logMessage("Tick: hour $hour")

      if (stopWhen(currentState)) {
        logMessage("Stop at hour: $hour")
        return hour
      }

      logMessage("state at $hour (begin): $currentState")

      currentState = eachHour(currentState)

      logMessage("state at $hour (end)  : $currentState")

      advanceToNextHour()

      if (hour == 100) throw RuntimeException("Too much iterations...")
    }
  }

  private fun advanceToNextHour() {
    hour++
  }

  private fun logMessage(msg: String) {
    if (logEnabled) println(msg)
  }
}
