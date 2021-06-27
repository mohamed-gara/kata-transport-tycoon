package application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class ApplicationTest {

  @TestFactory
  fun `compute transport duration for only B destination`(): List<DynamicTest> = tests(
    "" to 0,
    "B" to 5,
    "BB" to 5,
    "BBB" to 15,
    "BBBB" to 15,
    "BBBBB" to 25,
    "BBBBBB" to 25,
    "BBBBBBB" to 35,
    "BBBBBBBB" to 35,
  )

  @TestFactory
  fun `compute transport duration for only A destination`(): List<DynamicTest> = tests(
    "" to 0,
    "A" to 5,
    "AA" to 13,
  )

  fun tests(vararg tests: Pair<String, Int>): List<DynamicTest> =
    listOf(*tests).map { (containers, expectedDuration) ->
      dynamicTest("<$containers> are transported in $expectedDuration hours") {
        val sut = application.Application()

        val result = sut.transport(containers)

        assertThat(result).isEqualTo(expectedDuration)
      }
    }
}
