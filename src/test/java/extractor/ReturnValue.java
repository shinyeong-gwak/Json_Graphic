package extractor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ReturnValue {
    //double[]값 or String값으로 반환
    public static Object getLocationValue(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            if (jsonArray.size() == 3 && jsonArray.get(0).isJsonPrimitive() &&
                    jsonArray.get(1).isJsonPrimitive() && jsonArray.get(2).isJsonPrimitive()) {
                double[] locationArray = {
                        jsonArray.get(0).getAsDouble(),
                        jsonArray.get(1).getAsDouble(),
                        jsonArray.get(2).getAsDouble()
                };
                return locationArray;
            }
        } else if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        return null;
    }
}
