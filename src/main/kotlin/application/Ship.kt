package application

data class Ship(
  private val trip: Trip = Trip(0),
): ContainerHandler {

  override fun tryCarryContainer(trip: Trip): Ship =
    if (this.trip.inProgress) this
    else copy(trip=trip)

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
