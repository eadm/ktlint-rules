package ru.nobird.android.ktlint.rules

import org.junit.Test

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 09-Feb-21
 * https://github.com/bitvale
 */
class IfElseRuleTest {
    private val rule = IfElseRule()

    @Test
    fun `if statement shorter than 100 symbols`() {
        assertNoLintErrors(
            """
                private fun hello() {
                    val x = if (a > 13) 100 else 500
                }
            """,
            rule
        )
    }

    @Test
    fun `if statement equal 100 symbols`() {
        assertNoLintErrors(
            """
                private fun hello() {
                    val x = if (a > 13) 100 else 5000000000000000000000000000000000000000000000000000000000000000000000000000000
                }
            """,
            rule
        )
    }

    @Test
    fun `if statement longer than 100 symbols`() {
        assertFormat(
            """
                private fun hello() {
                    val x = if (a > 13) 100 else 50000000000000000000000000000000000000000000000000000000000000000000000000000000
                }
            """,
            """
                private fun hello() {
                    val x = 
                        if (a > 13) {
                            100
                        } else {
                            50000000000000000000000000000000000000000000000000000000000000000000000000000000
                        }
                }
            """,
            rule
        )
    }

    @Test
    fun `if statement with brace on one line`() {
        assertFormat(
            """
                private fun hello() {
                    val x = if (a > 13) { 100 + 450 } else { 500 }
                }
            """,
            """
                private fun hello() {
                    val x = 
                        if (a > 13) {
                            100 + 450
                        } else {
                            500
                        }
                }
            """,
            rule
        )
    }

    @Test
    fun `if statement with brace on one line with incorrect indent`() {
        assertFormat(
            """
                private fun hello() {
                    val x = if (a > 13) {          100 + 450         }    else { 500          }
                }
            """,
            """
                private fun hello() {
                    val x = 
                        if (a > 13) {
                            100 + 450
                        } else {
                            500
                        }
                }
            """,
            rule
        )
    }

    @Test
    fun `if statement with brace on two lines`() {
        assertFormat(
            """
                private fun hello() {
                    val x = if (a > 13) { 100 }
                    else { 500 }
                }
            """,
            """
                private fun hello() {
                    val x = 
                        if (a > 13) {
                            100
                        } else {
                            500
                        }
                }
            """,
            rule
        )
    }

    @Test
    fun `incorrect if statement 1`() {
        assertFormat(
            """
                private fun hello() {
                    val x = if (a > 13)
                            100
                        else
                            500
                }
            """,
            """
                private fun hello() {
                    val x = 
                        if (a > 13) {
                            100
                        } else {
                            500
                        }
                }
            """,
            rule
        )
    }

    @Test
    fun `incorrect if statement 2`() {
        assertFormat(
            """
                private fun hello() {
                    val x =
                        if (a > 13)
                            100
                        else
                            500
                }
            """,
            """
                private fun hello() {
                    val x = 
                        if (a > 13) {
                            100
                        } else {
                            500
                        }
                }
            """,
            rule
        )
    }

    @Test
    fun `incorrect if statement 3`() {
        assertFormat(
            """
                private fun hello() {
                    val x =
                    
                        if (a > 13)
                            100
                        else
                            500
                }
            """,
            """
                private fun hello() {
                    val x = 
                        if (a > 13) {
                            100
                        } else {
                            500
                        }
                }
            """,
            rule
        )
    }
}