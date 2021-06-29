package application

data class Port(
  val containersAtPort: Int = 0
) {

  fun hasNoContainerToDeliver(): Boolean =
    containersAtPort == 0

  fun peekNextContainerToDeliver(): Port =
    copy(containersAtPort=containersAtPort-1)

  fun putInWarehouse(numberOfTrucksArrivedToPort: Int): Port =
    copy(containersAtPort = containersAtPort + numberOfTrucksArrivedToPort)
}
