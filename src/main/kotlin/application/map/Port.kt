package application.map

import application.carriers.Container
import java.util.*

data class Port(
  val containersAtPort: List<Container> = listOf()
) {

  fun hasNoContainerToDeliver(): Boolean =
    containersAtPort.isEmpty()

  fun nextContainerToDeliver(): Optional<Container> =
    if (containersAtPort.isNotEmpty()) Optional.of(containersAtPort.first())
    else Optional.empty()

  fun peekNextContainerToDeliver(): Port =
    copy(containersAtPort=containersAtPort.drop(1))

  fun putInWarehouse(containersArrivedToPort: List<Container>): Port =
    copy(containersAtPort = containersAtPort + containersArrivedToPort)
}
