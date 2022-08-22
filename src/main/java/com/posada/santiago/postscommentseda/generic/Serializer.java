package com.posada.santiago.postscommentseda.generic;

import com.google.gson.Gson;

public class Serializer {

    private Gson gson;

    public static Serializer Instance(){
        return new Serializer();
    }

    public String serialize(DomainEvent event){
        return gson.toJson(event);
    }

    public DomainEvent deserialize(String body, String type) throws ClassNotFoundException {
        return (DomainEvent) gson.fromJson(body, Class.forName(type));
    }

}
