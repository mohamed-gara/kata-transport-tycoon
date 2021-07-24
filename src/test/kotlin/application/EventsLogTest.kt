package application

import application.carriers.Cargo
import application.carriers.TransportEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EventsLogTest {

  @Test
  fun `domain events for A`() {
    val app = Application()

    app.transport("A")

    assertThat(app.state.events)
      .containsExactly(
        TransportEvent(
          id = "",
          event = "DEPART",
          time = 0,
          transport_id = 0,
          kind = "TRUCK",
          location = "FACTORY",
          destination = "PORT",
          cargo = listOf(
            Cargo(
              cargo_id = "0",
              destination = "A",
              origin = "FACTORY",
            )
          ),
        )
      )
  }
}
