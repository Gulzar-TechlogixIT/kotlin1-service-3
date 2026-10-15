package org.example

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AppTest {
    @Test
    fun messagePayloadReportsKotLib3Version() {
        val payload = messagePayload()
        assertContains(payload, "\"name\":\"kot-lib-3\"")
        assertContains(payload, "\"version\":\"0.1.0\"")
        assertEquals("0.1.0", Kotlib3.version())
    }
}
