package application

class Application {

  val simulator = TimeSimulator()

  fun transport(containers: String): Int {
    return simulator.finishHourFor(
      initialState = State(remainingContainerForTrucks=containers) ,
      eachHour = State::calculateNextState,
      stopWhen = State::allContainersAreDelivered
    )
  }
}

