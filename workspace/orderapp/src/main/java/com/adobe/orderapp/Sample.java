package com.adobe.orderapp;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;


public class Sample {
    public static void main(String[] args) throws Exception{
        var s = """
                    {
                        "title": "Sr. Software Engineer",
                        "personal": {
                            "firstName": "Raj",
                            "lastName": "Kumar",
                            "phone": "1234567890"
                        },
                        "programmingSkills": [
                            "Spring boot",
                            "React"
                        ]
                    }
                """;

        var patch = """
                    [
                        {"op": "replace", "path": "/title", "value" : "Team Lead"},
                        {"op": "remove", "path": "/personal/phone"},
                        {"op": "add", "path" :"/personal/email", "value": "raj@adobe.com"},
                        {"op" : "add" , "path": "/programmingSkills/1" , "value": "AWS"}
                    ]
                """;

        ObjectMapper mapper = new ObjectMapper();
        JsonPatch jsonPatch = JsonPatch.fromJson(mapper.readTree(patch));

        var target = jsonPatch.apply(mapper.readTree(s));
        System.out.println(target);
    }
}
