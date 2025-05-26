package ui

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * A simple test class to demonstrate testing in the project.
 */
class SimpleTest {
    
    @Test
    fun testSimpleAddition() {
        // Given
        val a = 2
        val b = 3
        
        // When
        val result = a + b
        
        // Then
        assertEquals(5, result, "Simple addition should work correctly")
    }
    
    @Test
    fun testStringConcatenation() {
        // Given
        val str1 = "Hello, "
        val str2 = "World!"
        
        // When
        val result = str1 + str2
        
        // Then
        assertEquals("Hello, World!", result, "String concatenation should work correctly")
    }
}