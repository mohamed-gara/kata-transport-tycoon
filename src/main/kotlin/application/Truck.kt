package application

data class Truck(
  val id: String,
  private val trip: Trip = Trip(0)
) : ContainerHandler {

  override fun tryCarryContainerTo(containerDestination: Char): Truck {
    if (trip.inProgress) return this
    return startDriveTo(containerDestination)
  }

  private fun startDriveTo(container: Char): Truck {
    val destinations = mapOf(
      'A' to 1,
      'B' to 5,
    )
    val duration: Int = destinations[container] ?: 0

    return copy(trip= Trip(duration))
  }

  override fun move(): Truck =
    when {
      trip.inProgress -> copy(trip = trip.advance())
      else -> this
    }

  override fun hasNoDeliveryInProgress(): Boolean {
    return !trip.deliveryInProgress
  }

  override fun isAtPort(): Boolean =
    trip.elapsedDuration == 1 && trip.duration == 1
}
