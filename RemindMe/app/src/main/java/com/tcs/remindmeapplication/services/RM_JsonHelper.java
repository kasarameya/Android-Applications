package com.tcs.remindmeapplication.services;

import com.google.gson.Gson;

/**
 * Created by 979129 on 10/8/2015.
 */
public class RM_JsonHelper {

    public static String serialise(Object jsonObject)
    {
        Gson gson=new Gson();
        String gsonString=gson.toJson(jsonObject);
        return gsonString;
    }

    public static <T> Object Deserialise(String jsonString,Class<T> jsonObject)
    {
        Gson gson=new Gson();
        Object gsonObject1=gson.fromJson(jsonString,jsonObject);
        return gsonObject1;
    }
}
