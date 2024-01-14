package Json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;


public class JsonParser {
    public static JsonData parse(File inputFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            Gson gson = new Gson();
            Type type = new TypeToken<JsonData>(){}.getType();
            JsonData jsonData = gson.fromJson(br, type);
            jsonData.stations.stream().forEach(s -> s.stationNumber = jsonData.stations.indexOf(s));
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
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