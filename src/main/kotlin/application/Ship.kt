package application

data class Ship(
  val id: String = "",
  val remaningHoursToDeliver: Int = 0,
  val remaningHoursToGoHome: Int = 0
): ContainerHandler {

  override fun tryTakeContainer(containerDestination: Char): Ship =
    if (isNavigating()) this
    else startNavigation()

  private fun isNavigating(): Boolean =
    remaningHoursToDeliver > 0 || remaningHoursToGoHome > 0

  private fun startNavigation(): Ship =
    copy(
      remaningHoursToDeliver = shipTripDuration,
      remaningHoursToGoHome = shipTripDuration
    )

  override fun move(): Ship =
    when {
      remaningHoursToDeliver > 0 -> copy(remaningHoursToDeliver = remaningHoursToDeliver - 1)
      remaningHoursToGoHome > 0 -> copy(remaningHoursToGoHome = remaningHoursToGoHome - 1)
      else -> this
    }

  override fun isAtPort(): Boolean {
    TODO("Not yet implemented")
  }

  override fun hasNoDeliveryInProgress(): Boolean {
    return remaningHoursToDeliver == 0
  }
}

const val shipTripDuration = 4
