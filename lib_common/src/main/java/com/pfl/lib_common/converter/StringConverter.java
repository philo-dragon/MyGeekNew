package com.pfl.lib_common.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018\4\28 0028.
 */

public class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {


    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString();
    }

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return new JsonPrimitive("");
        }
        return new JsonPrimitive(src);
    }
}
