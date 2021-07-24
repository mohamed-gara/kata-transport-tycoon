package application.simulator

import application.carriers.TransportEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.lang.RuntimeException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.*

data class TimeSimulator(
  private var hour: Int = 0,
  private val logEnabled: Boolean = true
) {

  lateinit var currentState: State

  fun calculateEndHour(
    initialState: State,
    eachHour: (State) -> State,
    stopWhen: (State) -> Boolean
  ): Int {

    currentState = initialState

    while (true) {
      logMessage("state at the beginning of $hour: $currentState")

      if (stopWhen(currentState)) {
        logMessage("Stop at hour: $hour")
        logDomainEvents("AB", currentState.events)
        return hour
      }

      currentState = eachHour(currentState)

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
