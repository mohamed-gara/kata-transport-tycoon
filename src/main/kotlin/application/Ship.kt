package application

data class Ship(
  private val trip: Trip = Trip(0),
): ContainerHandler {

  override fun tryCarryContainerTo(containerDestination: Char): Ship {
    return if (trip.inProgress) this
    else startNavigation()
  }

  private fun startNavigation(): Ship =
    copy(trip=Trip(portToADuration))

  override fun move(): Ship =
    when {
      trip.inProgress -> copy(trip = trip.advance())
      else -> this
    }

  override fun isAtPort(): Boolean {
    TODO("Not yet implemented")
  }

  override fun hasNoDeliveryInProgress(): Boolean =
    !trip.deliveryInProgress
}

const val portToADuration = 4
