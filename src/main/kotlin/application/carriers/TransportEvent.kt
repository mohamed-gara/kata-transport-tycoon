package application.carriers

data class TransportEvent(
  val id: String = "",
  val event: String,
  val time: Int,
  val transport_id: Int,
  val kind: String,
  val location: String,
  val destination: String = "",
  val cargo: List<Cargo> = listOf(),
) {

}

data class Cargo(
  val cargo_id: String,
  val destination: String,
  val origin: String,
) {

}
