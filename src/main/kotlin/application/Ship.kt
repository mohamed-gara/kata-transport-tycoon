package application

data class Ship(
  private val trip: Trip = Trip(0),
): ContainerHandler {

  override fun canCarryContainer(trip: Trip): Boolean =
    !this.trip.inProgress

  override fun carryContainer(trip: Trip): Ship =
    if (canCarryContainer(trip)) copy(trip=trip)
    else throw IllegalStateException("Ship is not available now.")

  override fun move(): Ship =
    when {
      trip.inProgress -> copy(trip = trip.advance())
      else -> this
    }

  override fun isAtPort(): Boolean {
    TODO("Not yet implemented")
  }

  override fun hasDeliveryInProgress(): Boolean =
    trip.deliveryInProgress
}
