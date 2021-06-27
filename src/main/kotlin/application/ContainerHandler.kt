package application

interface ContainerHandler {
  fun tryTakeContainer(container: Char): Boolean
  fun hasNoDeliveryInProgress(): Boolean
  fun move()
  fun isAtPort(): Boolean
}
