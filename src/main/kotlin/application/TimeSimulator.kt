package application

data class TimeSimulator(
  private var hour: Int = 0,
) {

  fun finishHourFor(
    eachHour: (State) -> State,
    stopWhen: (State) -> Boolean,
    initialState: State
  ): Int {

    var currentState = initialState

    while (true) {
      if (stopWhen(currentState)) return hour

      currentState = eachHour(currentState)

      advanceToNextHour()
    }
  }

  private fun advanceToNextHour() {
    hour++
  }
}
