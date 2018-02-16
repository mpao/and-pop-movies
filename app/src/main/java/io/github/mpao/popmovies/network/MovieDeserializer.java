package io.github.mpao.popmovies.network;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import io.github.mpao.popmovies.Movie;

public class MovieDeserializer implements JsonDeserializer<Movie[]> {

    @Override
    public Movie[] deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {

        JsonElement data = je.getAsJsonObject().get("results");
        return new Gson().fromJson(data, type);

    }

}
