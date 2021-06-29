package application

interface ContainerHandler {
  fun tryCarryContainer(trip: Trip): ContainerHandler
  fun move(): ContainerHandler

  fun hasDeliveryInProgress(): Boolean
  fun isAtPort(): Boolean
}
