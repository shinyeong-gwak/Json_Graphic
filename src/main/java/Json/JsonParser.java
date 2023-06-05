package Json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JsonParser {
    public static JsonData parse(File inputFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            Gson gson = new Gson();
            Type type = new TypeToken<JsonData>(){}.getType();
            return gson.fromJson(br, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}