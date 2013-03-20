package com.edmondapps.utils.android.json;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public interface JsonBuilder<T> {
	T build(long id, JsonReader reader) throws IOException;
}
