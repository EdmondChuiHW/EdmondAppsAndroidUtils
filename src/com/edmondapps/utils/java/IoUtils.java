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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Collection of common boiler-plates involving I/O operations.
 * 
 * @author Edmond
 * 
 */
public final class IoUtils {
	private IoUtils() {
		throw new AssertionError("nice try");
	}

	/**
	 * Constructs a new {@link URL} instance.
	 * <p/>
	 * This method is designed to avoid cases where the programmer guarantees
	 * the {@code String} is a valid {@link URL}, but is forced to catch a
	 * {@code MalformedURLException}.
	 * 
	 * @param url
	 *            passed to {@link URL#URL(String)} constructor
	 * @return a new {@link URL} instance
	 * @throws IllegalArgumentException
	 *             if {@code MalformedURLException} is thrown
	 */
	public static URL newURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Constructs an {@link URL} and call {@link URL#openConnection()}.
	 * 
	 * @see {@link URL#openConnection()}
	 * @param url
	 *            passed to {@link #newURL(String)}
	 * @return An instance of type {@link URLConnection}, it can be any of its
	 *         sub-type depending on the generic inference. You must ensure the
	 *         type-safety or an Exception will be thrown.
	 * @throws IOException
	 *             thrown by {@link URL#openConnection()}
	 */
	// let it crash run-time!
	@SuppressWarnings("unchecked")
	public static <C extends URLConnection> C newConnection(String url) throws IOException {
		return (C)newURL(url).openConnection();
	}

	/**
	 * Wraps the {@code InputStream} returned by
	 * {@link HttpURLConnection#getInputStream()} in a {@link BufferedReader}:
	 * <p/>
	 * {@code new BufferedReader(new InputStreamReader(c.getInputStream()))}
	 * 
	 * @param c
	 *            non-{@code null} reference to a {@link HttpURLConnection}
	 * @return A new instance of {@link BufferedReader}.
	 * @throws IOException
	 *             thrown by {@link HttpURLConnection#getInputStream()}
	 */
	public static BufferedReader newReader(HttpURLConnection c) throws IOException {
		return new BufferedReader(new InputStreamReader(c.getInputStream()));
	}

	/**
	 * Closes the resource and ignores the potential {@link IOException} thrown
	 * by {@link Closeable}.
	 * <p/>
	 * Should generally be used inside a {@code finally} block.
	 * 
	 * @param c
	 *            {@code null}-safe reference to a resource
	 */
	public static void quietClose(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				// intended to ignore
			}
		}
	}

	/**
	 * Calls {@link HttpURLConnection#disconnect()}.
	 * <p/>
	 * Should generally be used inside a {@code finally} block.
	 * 
	 * @param c
	 *            {@code null}-safe reference to a {@link HttpURLConnection}
	 */
	public static void quietDisconnet(HttpURLConnection c) {
		if (c != null) {
			c.disconnect();
		}
	}
}
