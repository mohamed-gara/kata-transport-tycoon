package application

interface ContainerHandler {
  fun canCarryContainer(trip: Trip): Boolean
  fun carryContainer(trip: Trip): ContainerHandler
  fun move(): ContainerHandler

  fun hasDeliveryInProgress(): Boolean
  fun isAtPort(): Boolean
}
