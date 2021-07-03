package application.map

import application.carriers.Container

data class Itinerary(
  val container: Container,
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

fun truckItineraryFor(container: Container): Itinerary {
  val destinations = mapOf(
    "A" to 1,
    "B" to 5,
  )
  val duration: Int = destinations[container.destination] ?: 0
  return Itinerary(container, duration)
}

fun shipItineraryFor(container: Container): Itinerary =
  Itinerary(container, 4)
