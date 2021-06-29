package application

import java.util.*

data class Port(
  val containersAtPort: Int = 0
) {

  fun hasNoContainerToDeliver(): Boolean =
    containersAtPort == 0

  fun nextContainerToDeliver(): Optional<Int> =
    if (containersAtPort > 0) Optional.of(1)
    else Optional.empty()

  fun peekNextContainerToDeliver(): Port =
    copy(containersAtPort=containersAtPort-1)

  fun putInWarehouse(numberOfTrucksArrivedToPort: Int): Port =
    copy(containersAtPort = containersAtPort + numberOfTrucksArrivedToPort)
}
