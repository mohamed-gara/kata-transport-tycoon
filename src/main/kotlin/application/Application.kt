package application

class Application {

  private val simulator = TimeSimulator()

  fun transport(containersDestinations: String): Int =
    simulator.calculateEndHour(
      initialState = State(Factory(containersDestinations)) ,
      eachHour     = State::calculateNextState,
      stopWhen     = State::allContainersAreDelivered
    )
}

