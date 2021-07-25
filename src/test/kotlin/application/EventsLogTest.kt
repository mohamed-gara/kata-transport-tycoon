package application

import application.carriers.Cargo
import application.carriers.TransportEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EventsLogTest {

  @Test fun `domain events for AB`() {
    val app = Application()

    app.transport("AB")

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
        ),
        TransportEvent(
          id = "",
          event = "DEPART",
          time = 0,
          transport_id = 1,
          kind = "TRUCK",
          location = "FACTORY",
          destination = "B",
          cargo = listOf(
            Cargo(
              cargo_id = "1",
              destination = "B",
              origin = "FACTORY",
            )
          ),
        ),
        TransportEvent(
          id = "",
          event ="ARRIVE",
          time =1,
          transport_id =0,
          kind ="TRUCK",
          location ="PORT",
          destination = "",
          cargo = listOf(
            Cargo(
              cargo_id = "0",
              destination ="A",
              origin ="FACTORY"
            )
          )
        ),
        TransportEvent(
          id = "",
          event ="DEPART",
          time = 1,
          transport_id = 0,
          kind ="TRUCK",
          location ="PORT",
          destination = "FACTORY",
          cargo = listOf()
        ),
      )
  }
}
