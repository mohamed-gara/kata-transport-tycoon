package application

data class Truck(
  val id: String,
  private val trip: Trip = Trip(0)
) : ContainerHandler {

  override fun canCarryContainer(trip: Trip): Boolean =
    !this.trip.inProgress

  override fun carryContainer(trip: Trip): Truck =
    if (canCarryContainer(trip)) copy(trip= trip)
    else throw IllegalStateException("Truck $id is not available now.")

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
