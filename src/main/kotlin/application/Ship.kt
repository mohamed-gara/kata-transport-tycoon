package application

data class Ship(
  private val remainingHoursToDeliver: Int = 0,
  private val remainingHoursToGoHome: Int = 0
): ContainerHandler {

  override fun tryCarryContainerTo(containerDestination: Char): Ship =
    if (isNavigating()) this
    else startNavigation()

  private fun isNavigating(): Boolean =
    remainingHoursToDeliver > 0 || remainingHoursToGoHome > 0

  private fun startNavigation(): Ship =
    copy(
      remainingHoursToDeliver = portToADuration,
      remainingHoursToGoHome = portToADuration
    )

  override fun move(): Ship =
    when {
      remainingHoursToDeliver > 0 -> copy(remainingHoursToDeliver = remainingHoursToDeliver - 1)
      remainingHoursToGoHome > 0 -> copy(remainingHoursToGoHome = remainingHoursToGoHome - 1)
      else -> this
    }

  override fun isAtPort(): Boolean {
    TODO("Not yet implemented")
  }

  override fun hasNoDeliveryInProgress(): Boolean {
    return remainingHoursToDeliver == 0
  }
}

const val portToADuration = 4
