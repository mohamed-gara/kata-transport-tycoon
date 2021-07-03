package application

import application.map.factoryWith
import application.simulator.State
import application.simulator.TimeSimulator

class Application {

  private val simulator = TimeSimulator()

  fun transport(containersDestinations: String): Int =
    simulator.calculateEndHour(
      initialState = State(factoryWith(containersDestinations)) ,
      eachHour     = State::calculateNextState,
      stopWhen     = State::allContainersAreDelivered
    )
}

