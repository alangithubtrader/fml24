package com.example.fml24.fml24;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adu on 16-07-16.
 */
public class Common {

    public static JSONObject getJsonObject(JSONArray jsonArray, int index) throws JSONException {
        return jsonArray.getJSONObject(index);
    }

    public static String getValueFromJsonObject(JSONArray jsonArray, int index, String name) throws JSONException {
        JSONObject jsonObject = getJsonObject(jsonArray, index);

        String value = jsonObject.getString(name);
        return value;

    }


}
