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
package com.edmondapps.utils.android.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import android.os.AsyncTask;

import com.edmondapps.utils.android.Logs;
import com.edmondapps.utils.java.IoUtils;
import com.edmondapps.utils.java.IoUtils.ProgressCallback;

/**
 * @author Edmond
 * 
 */
public abstract class FileUploadService {
    public static final String TAG = "FileUploadService";
    // Line separator required by multipart/form-data.
    private static final String CRLF = "\r\n";
    private static final String HEADER_NAME = "Upload_0";

    private FileUploadService() {
        throw new AssertionError("nice try");
    }

    public interface Callback {
        /**
         * Called when progress is made.
         */
        void onProgress(long progress, long total);

        /**
         * Called when the task is finished or canceled.
         * 
         * @param success
         *            if the operation was finished successfully
         */
        void onDone(boolean success);
    }

    /**
     * Uploads a {@link File} synchronously.
     * 
     * @param file
     *            the file to be uploaded
     * @param where
     *            the destination url
     * @return if the upload was successful
     */
    public static boolean uploadFile(File file, String where) {
        return uploadFile(file, where, null);
    }

    /**
     * Uploads a {@link File} asynchronously.
     * 
     * @param file
     *            the file to be uploaded
     * @param where
     *            the destination url
     * @param callback
     *            the callback (invoked in the same thread as the one calling
     *            this method)
     * @return an {@link AsyncTask} reference, you may cancel the task with this
     *         reference
     */
    public static AsyncTask<?, ?, ?> uploadFileAsync(File file, String where, Callback callback) {
        return new FileUploadTask(file, where, callback).execute((Void[])null);
    }

    /*
     * Ref:
     * http://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection
     * -to-fire-and-handle-http-requests/2793153#2793153
     */
    @SuppressWarnings("resource")
    private static boolean uploadFile(File file, String where, ProgressCallback callback) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader response = null;
        PrintWriter writer = null;

        // unique string
        String boundary = Long.toHexString(System.currentTimeMillis());

        try {
            conn = IoUtils.newConnection(where);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setChunkedStreamingMode(1024);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            inputStream = new FileInputStream(file);
            outputStream = conn.getOutputStream();
            writer = new PrintWriter(outputStream, true);

            // start of binary boundary.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"" + HEADER_NAME + "\"; filename=\"" + file.getName() + "\"").append(CRLF);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();

            IoUtils.inputToOutput(inputStream, outputStream, callback);
            outputStream.flush();

            // end of binary boundary.
            writer.append(CRLF).flush();

            // End of multipart/form-data.
            writer.append("--" + boundary + "--").append(CRLF);
            writer.flush();

            return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            Logs.e(TAG, e);
            return false;
        } finally {
            IoUtils.quietDisconnet(conn);
            IoUtils.quietClose(inputStream);
            IoUtils.quietClose(outputStream);
            IoUtils.quietClose(response);
            IoUtils.quietClose(writer);
        }
    }

    private static class FileUploadTask extends AsyncTask<Void, Long, Boolean> {
        private final File mFile;
        private final String mWhere;
        private final Callback mCallback;
        private final long mFileLength;

        private FileUploadTask(File file, String where, Callback callback) {
            mFile = file;
            mFileLength = file.length();
            mWhere = where;
            mCallback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return uploadFile(mFile, mWhere, new ProgressCallback() {
                @Override
                public boolean onProgress(long progress) {
                    publishProgress(progress, mFileLength);
                    return !isCancelled();
                }
            });
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            mCallback.onProgress(values[0], values[1]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mCallback.onDone(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mCallback.onDone(false);
        }
    }
}
