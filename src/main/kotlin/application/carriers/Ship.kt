package application.carriers

import application.map.Itinerary

data class Ship(
  private val itinerary: Itinerary = Itinerary(0),
): Carrier {

  override fun canCarryContainer(itinerary: Itinerary): Boolean =
    !this.itinerary.inProgress

  override fun carryContainer(itinerary: Itinerary): Ship =
    if (canCarryContainer(itinerary)) copy(itinerary=itinerary)
    else throw IllegalStateException("Ship is not available now.")

  override fun move(): Ship =
    when {
      itinerary.inProgress -> copy(itinerary = itinerary.advance())
      else -> this
    }

  override fun isAtPort(): Boolean {
    TODO("Not yet implemented")
  }

  override fun hasDeliveryInProgress(): Boolean =
    itinerary.deliveryInProgress
}
