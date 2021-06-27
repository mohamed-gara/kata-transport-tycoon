package application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class ApplicationTest {

  @TestFactory
  fun `compute transport duration for no container`(): List<DynamicTest> = tests(
    "" to 0,
  )

  @TestFactory
  fun `compute transport duration for only B destination`(): List<DynamicTest> = tests(
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
    "A" to 5,
    "AA" to 13,
    "AAA" to 21,
    "AAAA" to 29,
    "AAAAA" to 37,
    "AAAAAA" to 45,
  )

  @TestFactory
  fun `compute transport duration for both A and B destinations`(): List<DynamicTest> = tests(
    "AB" to 5,
    "ABB" to 7,
    "AABABBAB" to 29,
    "ABBBABAAABBB" to 41,
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
