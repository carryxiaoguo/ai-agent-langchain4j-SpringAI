package com.xiaoguo.guimagesearchmcpserver.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ImageSearchToolTest {

    @Test
    void canCreateToolWithoutCallingRemoteApi() {
        assertNotNull(new ImageSearchTool());
    }
}
