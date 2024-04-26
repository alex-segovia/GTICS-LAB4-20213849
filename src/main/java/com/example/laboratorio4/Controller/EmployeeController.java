package com.example.laboratorio4.Controller;

import com.example.laboratorio4.Entity.Employee;
import com.example.laboratorio4.Repository.DepartmentRepository;
import com.example.laboratorio4.Repository.EmployeeRepository;
import com.example.laboratorio4.Repository.JobRepository;
import com.example.laboratorio4.Repository.LocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class EmployeeController {

    final EmployeeRepository employeeRepository;
    final DepartmentRepository departmentRepository;
    final LocationRepository locationRepository;
    final JobRepository jobRepository;

    public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, LocationRepository locationRepository, JobRepository jobRepository){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.locationRepository = locationRepository;
        this.jobRepository = jobRepository;
    }

    @GetMapping(value = "/empleados")
    public String listarEmpleados(Model model){
        model.addAttribute("listaEmpleados",employeeRepository.listarEmpleados());
        return "principal";
    }

    @PostMapping(value = "/empleados/buscar")
    public String listarEmpleadosBusqueda(Model model, @RequestParam(value = "busqueda")String busqueda){
        model.addAttribute("listaEmpleados",employeeRepository.listarEmpleadosBusqueda("%"+busqueda+"%"));
        model.addAttribute("busquedaRealizada",busqueda);
        return "principal";
    }

    @GetMapping(value = "/empleados/editar")
    public String editarEmpleados(Model model, @RequestParam(value = "id")int id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            model.addAttribute("employee",employee);
            model.addAttribute("listaDepartamentos",departmentRepository.findAll());
            model.addAttribute("listaCiudades",locationRepository.findAll());
            model.addAttribute("listaPuestos",jobRepository.findAll());
            return "editar";
        }else{
            return "redirect:/empleados";
        }
    }

    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(@RequestParam(value = "department")int departamento,
                                  @RequestParam(value = "location")int ciudad,
                                  @RequestParam(value = "job")int puesto,
                                  @RequestParam(value = "employeeId")int id) {
        employeeRepository.editarEmpleados(departamento,puesto, id);
        return "redirect:/empleados";
    }

    @PostMapping("/empleados/guardarNuevo")
    public String registrarEmpleado(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/empleados";
    }

    @GetMapping("/empleados/borrar")
    public String borrarEmpleado(@RequestParam("id") int id) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        }
        return "redirect:/empleados";

    }

    @GetMapping("/empleados/nuevo")
    public String nuevoEmpleado(Model model) {
        model.addAttribute("listaDepartamentos",departmentRepository.findAll());
        model.addAttribute("listaPuestos",jobRepository.findAll());
        return "registrar";
    }
}
