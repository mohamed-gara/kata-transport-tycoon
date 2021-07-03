package application.carriers

import application.map.Itinerary


data class Carrier(
  val id: String,
  val currentItinerary: Itinerary = NO_ITINERARY
) {

  fun carryContainer(itinerary: Itinerary): Carrier =
    if (canCarryContainer()) copy(currentItinerary= itinerary)
    else throw IllegalStateException("$id is not available now.")

  private fun canCarryContainer(): Boolean =
    !this.currentItinerary.inProgress

  fun move(): Carrier =
    when {
      currentItinerary.inProgress -> copy(currentItinerary = currentItinerary.advance())
      else -> this
    }

  fun hasDeliveryInProgress(): Boolean =
    currentItinerary.deliveryInProgress

  fun isAtDeparture(): Boolean =
    !currentItinerary.inProgress

  fun isAtDestination(): Boolean =
    currentItinerary.elapsedDuration == currentItinerary.duration

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Carrier

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }
}

val NO_ITINERARY = Itinerary(Container("", "", ""), 0)

fun Set<Carrier>.moveAll() =
  map { it.move() }.toSet()

fun List<Carrier>.noneHasDeliveryInProgress() =
  none { it.hasDeliveryInProgress() }
