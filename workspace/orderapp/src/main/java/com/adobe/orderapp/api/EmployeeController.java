package com.adobe.orderapp.api;

import com.adobe.orderapp.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    Employee employee = new Employee();

    public EmployeeController() {
        employee.setId(1);
        employee.setTitle("Sr. Senior Programmer");
        var personal = new HashMap<String, String>();
        personal.put("firstName", "Smitha");
        personal.put("lastName", "Patil");
        personal.put("phone", "1224555");
        employee.setPersonal(personal);

        var programmingSkills = new ArrayList<String>();
        programmingSkills.add("Spring Boot");
        programmingSkills.add("React");
        employee.setProgrammingSkills(programmingSkills);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public Employee updateEmployee(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(patch);
        var jsonNodeTarget = patch.apply(mapper.readTree(mapper.writeValueAsString(employee)));
        return  mapper.treeToValue(jsonNodeTarget, Employee.class); // Convert JSONNode to Employee
    }

}
