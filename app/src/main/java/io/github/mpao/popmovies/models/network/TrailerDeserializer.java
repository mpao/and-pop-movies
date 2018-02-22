package io.github.mpao.popmovies.models.network;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import io.github.mpao.popmovies.entities.Trailer;

public class TrailerDeserializer implements JsonDeserializer<Trailer[]> {

    @Override
    public Trailer[] deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {

        /*
         * Get an array of trailer for the movie passed as parameter.
         */
        JsonElement data = je.getAsJsonObject().get("results");
        return new Gson().fromJson(data, type);

    }

}