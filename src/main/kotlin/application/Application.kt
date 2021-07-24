package application

import application.map.factoryWith
import application.simulator.State
import application.simulator.TimeSimulator

class Application {

  val simulator = TimeSimulator()
  val state: State
    get() = simulator.currentState

  fun transport(containersDestinations: String): Int =
    simulator.calculateEndHour(
      initialState = State(factoryWith(containersDestinations)) ,
      eachHour     = State::calculateNextState,
      stopWhen     = State::allContainersAreDelivered
    )
}

