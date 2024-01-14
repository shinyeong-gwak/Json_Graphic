package Json;

import com.google.gson.JsonElement;

import java.util.List;

public class Ap {
    public JsonElement location;
    public List<Link> links;

    public Class<?> stateLoacation() {
        if(location.isJsonArray()) {
            return double[].class;
        } else if (location.isJsonPrimitive()) {
            return String.class;
        } else {
            System.out.println("이 객체는 아닐텐데...");
            throw new NullPointerException();
        }
    }




}
