package application.carriers

import application.map.Itinerary

data class Truck(
  val id: String,
  private val itinerary: Itinerary = Itinerary(0)
) : Carrier {

  override fun canCarryContainer(itinerary: Itinerary): Boolean =
    !this.itinerary.inProgress

  override fun carryContainer(itinerary: Itinerary): Truck =
    if (canCarryContainer(itinerary)) copy(itinerary= itinerary)
    else throw IllegalStateException("Truck $id is not available now.")

  override fun move(): Truck =
    when {
      itinerary.inProgress -> copy(itinerary = itinerary.advance())
      else -> this
    }

  override fun hasDeliveryInProgress(): Boolean {
    return itinerary.deliveryInProgress
  }

  override fun isAtPort(): Boolean =
    itinerary.elapsedDuration == 1 && itinerary.duration == 1
}
