package com.splearn.adapter.integration

import com.splearn.domain.Email
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junitpioneer.jupiter.StdIo
import org.junitpioneer.jupiter.StdOut

class DummyEmailSenderTest {
    @Test
    @StdIo
    fun dummyEmailSender(out: StdOut) {
        val dummyEmailSender = DummyEmailSender()

        dummyEmailSender.send(Email("hodako@splearn.app"), "yeah!!!", "important: dummy")
        val expected = "DummyEmailSender sending email " + Email("hodako@splearn.app").toString()
        assertThat(out.capturedLines()[0]).isEqualTo(expected)
    }
}
