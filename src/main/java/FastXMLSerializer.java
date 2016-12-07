import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class FastXMLSerializer {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String convert(Person person) {
        if (person == null)
            return null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(person);
        } catch (IOException e) {
            throw new RuntimeException("convert ModelResource object to json string exception:" + e.getMessage(), e);
        }
    }

    public static Person convert(String str) {
        try {
            if (str == null || str.isEmpty())
                return null;
            return mapper.readValue(str, Person.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}