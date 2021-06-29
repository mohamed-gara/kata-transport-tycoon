package application

data class Truck(
  val id: String,
  private val trip: Trip = Trip(0)
) : ContainerHandler {

  override fun tryCarryContainer(trip: Trip): Truck =
    if (this.trip.inProgress) this
    else copy(trip= trip)

  override fun move(): Truck =
    when {
      trip.inProgress -> copy(trip = trip.advance())
      else -> this
    }

  override fun hasDeliveryInProgress(): Boolean {
    return trip.deliveryInProgress
  }

  override fun isAtPort(): Boolean =
    trip.elapsedDuration == 1 && trip.duration == 1
}
