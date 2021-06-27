package application

interface ContainerHandler {
  fun tryTakeContainer(containerDestination: Char): ContainerHandler
  fun hasNoDeliveryInProgress(): Boolean
  fun move(): ContainerHandler
  fun isAtPort(): Boolean
}
