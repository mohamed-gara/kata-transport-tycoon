package application.carriers

import application.map.Itinerary

interface Carrier {
  fun canCarryContainer(itinerary: Itinerary): Boolean
  fun carryContainer(itinerary: Itinerary): Carrier
  fun hasDeliveryInProgress(): Boolean

  fun move(): Carrier
  fun isAtPort(): Boolean
}
