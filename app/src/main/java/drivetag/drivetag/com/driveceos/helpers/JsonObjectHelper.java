package drivetag.drivetag.com.driveceos.helpers;

import com.google.gson.JsonObject;

/**
 * Created by yuriy on 3/23/17.
 */

public class JsonObjectHelper {
    public static Boolean hasValueFromKey(String key, JsonObject jsonObject) {
        if (jsonObject != null && jsonObject.has(key) && !jsonObject.get(key).isJsonNull()) {
            return true;
        } else {
            return false;
        }
    }
}