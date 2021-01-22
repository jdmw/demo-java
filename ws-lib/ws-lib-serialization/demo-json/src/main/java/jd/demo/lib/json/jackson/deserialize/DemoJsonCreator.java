package jd.demo.lib.json.jackson.deserialize;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.util.Assert;
import lombok.Data;

/**
 *
 * @JsonCreator tells Jackson deserializer to use the designated constructor for deserialization.
 * https://www.baeldung.com/jackson-deserialize-immutable-objects
 */
public class DemoJsonCreator {
    @Data
    public static final class Employee {

        private long id;
        private String name;

        private Employee(){

        }
        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public Employee(@JsonProperty("id") long id, @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }

        // getters
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Employee jack = new Employee(1L, "Jack");
        String json = mapper.writeValueAsString(jack);
        Employee employee = mapper.readValue(json, Employee.class);
        Assert.check(jack.equals(employee));
    }
}


