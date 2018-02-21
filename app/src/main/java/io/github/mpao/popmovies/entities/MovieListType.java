package io.github.mpao.popmovies.entities;

public enum MovieListType {

    POPULAR("pop"), TOP_RATED("top"), FAVORITES("fav");

    private final String value;

    MovieListType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
