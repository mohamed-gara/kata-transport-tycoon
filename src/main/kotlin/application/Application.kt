package application

import application.map.Factory
import application.simulator.State
import application.simulator.TimeSimulator

class Application {

  private val simulator = TimeSimulator()

  fun transport(containersDestinations: String): Int =
    simulator.calculateEndHour(
      initialState = State(Factory(containersDestinations)) ,
      eachHour     = State::calculateNextState,
      stopWhen     = State::allContainersAreDelivered
    )
}

