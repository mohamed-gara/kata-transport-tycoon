package application.map

import application.carriers.Container
import java.util.*

data class Factory(
  val containersAtFactory: List<Container>
) {

  fun hasNoContainerToDeliver(): Boolean =
    containersAtFactory.isEmpty()

  fun nextContainerToDeliver(): Optional<Container> =
    if (containersAtFactory.isNotEmpty()) Optional.of(containersAtFactory.first())
    else Optional.empty()

  fun peekNextContainersToDeliver(): Factory =
    copy(containersAtFactory=containersAtFactory.drop(1))
}

fun factoryWith(containersDestinations: String) =
  Factory(containersDestinations.toList().map { Container("", "FACTORY", it.toString()) })
