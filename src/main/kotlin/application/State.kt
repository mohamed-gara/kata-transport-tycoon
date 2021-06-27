package application

data class State(
  var remainingContainerForTrucks: String = "",
  var remainingContainerForShip: Int = 0,
  val truck1: Truck = Truck("1"),
  val truck2: Truck = Truck("2"),
  val ship: Ship = Ship(),
) {

}
