package application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class ApplicationTest {

  private val sut = Application()

  @TestFactory
  fun `compute transport duration`() =
    listOf(
      "A" to 5,
      "AB" to 5
    ).map { (containers, expectedDuration) ->
      dynamicTest("$containers are transported in $expectedDuration hours") {
        val result = sut.transport(containers)

        assertThat(result).isEqualTo(expectedDuration)
      }
    }
}
