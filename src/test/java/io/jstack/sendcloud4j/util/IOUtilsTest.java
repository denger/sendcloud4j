package io.jstack.sendcloud4j.util;


import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class IOUtilsTest {

    @Test
    public void testReadExistsFile() {
        byte[] bt = null;
        try {
            bt = IOUtils.read(new File("src/test/resouces/3byte_test.txt"));
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
        assertNotNull(bt);
        assertEquals(bt.length, 3);
    }

    @Test(expected = IOException.class)
    public void testReadNotExistsFileThrowException() throws IOException {
        IOUtils.read(new File("src/test/resouces/not_exists_file.txt"));
    }

    @Test(expected = IOException.class)
    public void testReadEmptyFileThrowException() throws IOException {
        IOUtils.read(new File("src/test/resouces/empty.txt"));
    }
}
