package application

data class Truck(
  val id: String,
  private val remainingHoursToDeliver: Int = 0,
  private val remainingHoursToGoHome: Int = 0,
  private val destinationIsPort: Boolean = false,
) : ContainerHandler {

  override fun move(): Truck =
    when {
      remainingHoursToDeliver > 0 -> copy(remainingHoursToDeliver = remainingHoursToDeliver - 1)
      remainingHoursToGoHome > 0 -> copy(remainingHoursToGoHome = remainingHoursToGoHome - 1)
      else -> this
    }

  override fun tryCarryContainerTo(containerDestination: Char): Truck {
    if (isDriving()) return this
    return startDriveTo(containerDestination)
  }

  private fun isDriving() = remainingHoursToDeliver > 0 || remainingHoursToGoHome > 0

  private fun startDriveTo(container: Char): Truck {
    val destinations = mapOf(
      'A' to 1,
      'B' to 5,
    )
    val duration: Int = destinations[container] ?: 0

    return copy(
      remainingHoursToDeliver = duration,
      remainingHoursToGoHome = duration,
      destinationIsPort = duration == 1
    )
  }

  override fun hasNoDeliveryInProgress(): Boolean {
    return remainingHoursToDeliver == 0
  }

  override fun isAtPort(): Boolean {
    return destinationIsPort && remainingHoursToDeliver == 0 && remainingHoursToGoHome == 1
  }
}
