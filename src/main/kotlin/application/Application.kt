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
    tryTakingNextContainerByTruck(state, state.truck1)
    tryTakingNextContainerByTruck(state, state.truck2)
    tryTakingNextContainerByShip(state, state.ship)

    state.containerHandlers.forEach { it.move() }

    state.trucks.forEach {
      if (it.isAtPort()) state.remainingContainerForShip++
    }

    return state
  }

  private fun tryTakingNextContainerByTruck(state: State, truck: ContainerHandler) {
    if (state.remainingContainerForTrucks.isBlank()) return
    if (truck.tryTakeContainer(state.remainingContainerForTrucks.first())) {
      state.remainingContainerForTrucks = state.remainingContainerForTrucks.drop(1)
    }
  }

  private fun tryTakingNextContainerByShip(state: State, ship: ContainerHandler) {
    if (state.remainingContainerForShip == 0) return
    if (ship.tryTakeContainer('A')) {
      state.remainingContainerForShip--
    }
  }

  private fun allContainersAreDelivered(state: State): Boolean =
    state.remainingContainerForTrucks.isEmpty()
        && state.remainingContainerForShip == 0
        && state.truck1.hasNoDeliveryInProgress()
        && state.truck2.hasNoDeliveryInProgress()
        && state.ship.hasNoDeliveryInProgress()
}

