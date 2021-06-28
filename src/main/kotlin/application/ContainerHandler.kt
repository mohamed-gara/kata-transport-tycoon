package application

interface ContainerHandler {
  fun tryCarryContainerTo(containerDestination: Char): ContainerHandler
  fun move(): ContainerHandler

  fun hasNoDeliveryInProgress(): Boolean
  fun isAtPort(): Boolean
}
