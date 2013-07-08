/*
 * Copyright 2013 Edmond Chui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.edmondapps.utils.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.edmondapps.utils.java.IoUtils.ProgressCallback;

/**
 * @author Edmond
 * 
 */
public class IoTest {
    @Test
    public void fileCopy() {
        final String expected = generateCrazyString();
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(expected.getBytes()));
            outputStream = new ByteArrayOutputStream();
            IoUtils.inputToOutput(inputStream, outputStream, new ProgressCallback() {
                @Override
                public boolean onProgress(long progress) {
                    System.out.println(progress);
                    return true;
                }
            });

            outputStream.flush();
            String actual = outputStream.toString();
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            Assert.fail();
        } finally {
            IoUtils.quietClose(outputStream);
            IoUtils.quietClose(inputStream);
        }
    }

    private static String generateCrazyString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1337; ++i) {
            builder.append("string");
            builder.append(i);
        }
        return builder.toString();
    }

    @Test
    public void validUrl() {
        URL url = IoUtils.newURL("http://www.google.com");
        assertEquals("http://www.google.com", url.toExternalForm());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidUrl() {
        IoUtils.newURL("ht://www.google.com");
    }

    @Test
    public void close() {
        @SuppressWarnings("resource")
        SimpleClose c = new SimpleClose();
        IoUtils.quietClose(c);
        assertTrue(c.isClosed());
    }

    @Test
    public void closeNull() {
        Closeable c = null;
        IoUtils.quietClose(c);
    }

    private static class SimpleClose implements Closeable {
        private boolean mClosed;

        @Override
        public void close() throws IOException {
            mClosed = true;
            throw new IOException("Expected to be catched");
        }

        public boolean isClosed() {
            return mClosed;
        }
    }
}
