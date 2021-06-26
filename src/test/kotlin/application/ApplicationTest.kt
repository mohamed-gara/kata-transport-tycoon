package application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class ApplicationTest {

  @TestFactory
  fun `compute transport duration`() =
    listOf(
      "" to 0,
      "B" to 5,
      "BB" to 5,
      "BBB" to 15,
      "BBBB" to 15,
      "BBBBB" to 25,
      "BBBBBB" to 25,
    ).map { (containers, expectedDuration) ->
      dynamicTest("$containers are transported in $expectedDuration hours") {
        val sut = application.Application()

        val result = sut.transport(containers)

        assertThat(result).isEqualTo(expectedDuration)
      }
    }
}
