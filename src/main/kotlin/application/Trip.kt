package application

data class Trip(
  val duration: Int,
  val elapsedDuration: Int = 0,
) {
  val inProgress: Boolean
    get() = duration != 0 && elapsedDuration < (duration * 2)

  val deliveryInProgress: Boolean
    get() = duration != 0 && elapsedDuration < duration

  val goBackInProgress: Boolean
    get() = duration != 0 && elapsedDuration in (duration+1)..(duration * 2)

  fun advance(): Trip =
    if (inProgress) copy(elapsedDuration=elapsedDuration+1)
    else this
}

fun truckTripFor(destination: Char): Trip {
  val destinations = mapOf(
    'A' to 1,
    'B' to 5,
  )
  val duration: Int = destinations[destination] ?: 0
  return Trip(duration)
}
