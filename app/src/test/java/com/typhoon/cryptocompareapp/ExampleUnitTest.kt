package com.typhoon.cryptocompareapp

import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `Test failure`() {
        assertEquals("Wow! Can't be true!", 3, 1 + 1)
    }
}