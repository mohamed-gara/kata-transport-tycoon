package application

class Application {

  val simulator = TimeSimulator()

  fun transport(containers: String): Int {
    return simulator.finishHourFor(
      initialState = State(remainingContainerForTrucks=containers) ,
      eachHour = ::calculateNextState,
      stopWhen = ::allContainersAreDelivered
    )
  }

  private fun calculateNextState(state: State): State {
    tryTakingNextContainerBy(state, state.truck1)
    tryTakingNextContainerBy(state, state.truck2)
    tryTakingNextContainerBy(state, state.ship)

    state.truck1.move()
    state.truck2.move()
    state.ship.move()

    if (state.truck1.isAtPort()) state.remainingContainerForShip++
    if (state.truck2.isAtPort()) state.remainingContainerForShip++

    return state
  }

  private fun tryTakingNextContainerBy(state: State, truck: Truck) {
    if (state.remainingContainerForTrucks.isBlank()) return
    if (truck.tryTakeContainer(state.remainingContainerForTrucks.first()))
      state.remainingContainerForTrucks = state.remainingContainerForTrucks.drop(1)
  }

  private fun tryTakingNextContainerBy(state: State, ship: Ship) {
    if (state.remainingContainerForShip == 0) return
    if (ship.tryTakeContainer()) state.remainingContainerForShip--
  }

  private fun allContainersAreDelivered(state: State): Boolean =
    state.remainingContainerForTrucks.isEmpty()
        && state.remainingContainerForShip == 0
        && state.truck1.hasNoDeliveryInProgress()
        && state.truck2.hasNoDeliveryInProgress()
        && state.ship.hasNoDeliveryInProgress()
}

