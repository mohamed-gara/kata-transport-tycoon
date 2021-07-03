package application.carriers

import application.map.Itinerary

data class Carrier(
  val id: String,
  private val itinerary: Itinerary = Itinerary(0)
) {

  fun carryContainer(itinerary: Itinerary): Carrier =
    if (canCarryContainer(itinerary)) copy(itinerary= itinerary)
    else throw IllegalStateException("Truck $id is not available now.")

  private fun canCarryContainer(itinerary: Itinerary): Boolean =
    !this.itinerary.inProgress

  fun move(): Carrier =
    when {
      itinerary.inProgress -> copy(itinerary = itinerary.advance())
      else -> this
    }

  fun hasDeliveryInProgress(): Boolean =
    itinerary.deliveryInProgress

  fun isAtDestination(): Boolean =
    itinerary.elapsedDuration == itinerary.duration && itinerary.duration == 1

  fun isAtDeparture(): Boolean =
    !itinerary.inProgress

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

fun Set<Carrier>.moveAll() =
  map { it.move() }.toSet()

fun List<Carrier>.noneHasDeliveryInProgress() =
  none { it.hasDeliveryInProgress() }
