package com.xiaoguo.guaiagent.agent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class GuoManusTest {

    @Test
    void canCreateAgentWrapper() {
        assertNotNull(new GuoManus(mock(ManusAssistant.class)));
    }
}
