package application.map

import java.util.*

data class Factory(
  val containersAtFactory: String
) {

  fun hasNoContainerToDeliver(): Boolean =
    containersAtFactory.isEmpty()

  fun nextContainerToDeliver(): Optional<Char> =
    if (containersAtFactory.isNotEmpty()) Optional.of(containersAtFactory.first())
    else Optional.empty()

  fun peekNextContainersToDeliver(): Factory =
    copy(containersAtFactory=containersAtFactory.drop(1))
}
