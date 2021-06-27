package application

data class State(
  var remainingContainerForTrucks: String = "",
  var remainingContainerForShip: Int = 0,
  val truck1: ContainerHandler = Truck("1"),
  val truck2: ContainerHandler = Truck("2"),
  val ship: Ship = Ship(),
) {

}
