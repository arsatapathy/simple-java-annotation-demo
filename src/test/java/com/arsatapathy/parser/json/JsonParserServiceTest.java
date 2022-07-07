package com.arsatapathy.parser.json;

import com.arsatapathy.model.Person;
import com.arsatapathy.parser.json.exception.JsonParserException;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserServiceTest {

    @Test
    public void test_01() throws JsonParserException {
        Person person = new Person("ashish", "satapathy", "30");

        JsonParserService service = new JsonParserService();

        Assert.assertEquals("{\"firstName\":\"Ashish\",\"lastName\":\"Satapathy\",\"age\":\"30\"}", service.getJson(person));
    }
}
