package com.kingteller.client.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * json 工具类
 * Created by Administrator on 16-2-15.
 */
public class KingTellerJsonUtils {
	
	/**
	 * 生成json
	 */
	public static String convertJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
    /**
     * 解析json
     */
    public static <T> T fromJson(String json, Type typeofT) {
        Gson gson = new Gson();
        return gson.fromJson(json, typeofT);
    }

    public static List<String> getStringPersons(String jsonString) {
        List<String> list = new ArrayList<>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
        return list;
    }

    public static <T> T getPerson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            return null;
        }
        return t;
    }

    public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();

        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        ArrayList<JsonObject> jsonObjs = new Gson().fromJson(jsonString, type);

        try {
            Gson gson = new Gson();
            for (JsonObject jsonObj : jsonObjs) {
                list.add(gson.fromJson(jsonObj, cls));
            }
            //list = gson.fromJson(jsonString, new TypeToken<ArrayList<cls>>() {}.getType());
        } catch (Exception e) {
            return null;
        }
        return list;

    }
}
