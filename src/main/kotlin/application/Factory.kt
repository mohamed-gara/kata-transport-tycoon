package application

data class Factory(
  val containersAtFactory: String
) {

  fun hasNoContainerToDeliver(): Boolean =
    containersAtFactory.isEmpty()

  fun nextContainerToDeliver(): Char =
    containersAtFactory.first()

  fun peekNextContainerToDeliver(): String =
    containersAtFactory.drop(1)
}
