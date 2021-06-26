package application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class ApplicationTest {

  private val sut = application.Application()

  @TestFactory
  fun `compute transport duration`() =
    listOf(
      "" to 0,
    ).map { (containers, expectedDuration) ->
      dynamicTest("$containers are transported in $expectedDuration hours") {
        val result = sut.transport(containers)

        assertThat(result).isEqualTo(expectedDuration)
      }
    }
}
