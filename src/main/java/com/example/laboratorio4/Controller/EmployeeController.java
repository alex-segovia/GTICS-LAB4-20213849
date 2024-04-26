package com.example.laboratorio4.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {
    @GetMapping(value = "/empleados")
    public String listarEmpleados(){
        return "principal";
    }
}
