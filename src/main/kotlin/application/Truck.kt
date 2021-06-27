package application

data class Truck(
  val id: String,
  val remaningHoursToDeliver: Int = 0,
  val remaningHoursToGoHome: Int = 0,
  val targetIsPort: Boolean = false,
) : ContainerHandler {

  override fun move(): Truck =
    when {
      remaningHoursToDeliver > 0 -> copy(remaningHoursToDeliver = remaningHoursToDeliver - 1)
      remaningHoursToGoHome > 0 -> copy(remaningHoursToGoHome = remaningHoursToGoHome - 1)
      else -> this
    }

  override fun tryTakeContainer(containerDestination: Char): Truck {
    if (isDriving()) return this
    return startDriveTo(containerDestination)
  }

  private fun isDriving() = remaningHoursToDeliver > 0 || remaningHoursToGoHome > 0

  private fun startDriveTo(container: Char): Truck {
    val destinations = mapOf(
      'A' to 1,
      'B' to 5,
    )
    val duration: Int = destinations[container] ?: 0

    return copy(
      remaningHoursToDeliver = duration,
      remaningHoursToGoHome = duration,
      targetIsPort = duration == 1
    )
  }

  override fun hasNoDeliveryInProgress(): Boolean {
    return remaningHoursToDeliver == 0
  }

  override fun isAtPort(): Boolean {
    return targetIsPort && remaningHoursToDeliver == 0 && remaningHoursToGoHome == 1
  }
}
