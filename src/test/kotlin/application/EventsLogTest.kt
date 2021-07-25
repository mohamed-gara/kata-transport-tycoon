package application

import application.carriers.TransportEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EventsLogTest {

  @Test fun `domain events for AB`() {
    val app = Application()

    app.transport("AB")

    assertThat(app.state.events)
      .containsExactlyElementsOf(expectedEventsFrom("AB_expected_events.json"))
  }

  fun expectedEventsFrom(file: String): List<TransportEvent> {
    val fileContent = javaClass.classLoader.getResourceAsStream(file)
    return jacksonObjectMapper().readValue(fileContent)
  }
}
