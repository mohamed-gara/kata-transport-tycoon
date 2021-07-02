package application.map

data class Itinerary(
  val duration: Int,
  val elapsedDuration: Int = 0,
) {
  val inProgress: Boolean
    get() = duration != 0 && elapsedDuration < (duration * 2)

  val deliveryInProgress: Boolean
    get() = duration != 0 && elapsedDuration < duration

  val goBackInProgress: Boolean
    get() = duration != 0 && elapsedDuration in (duration+1)..(duration * 2)

  fun advance(): Itinerary =
    if (inProgress) copy(elapsedDuration=elapsedDuration+1)
    else this
}

fun truckItineraryFor(destination: Char): Itinerary {
  val destinations = mapOf(
    'A' to 1,
    'B' to 5,
  )
  val duration: Int = destinations[destination] ?: 0
  return Itinerary(duration)
}
