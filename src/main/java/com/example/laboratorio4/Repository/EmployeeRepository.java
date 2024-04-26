package com.example.laboratorio4.Repository;

import com.example.laboratorio4.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query(nativeQuery = true, value = "select * from employees")
    List<Employee> listarEmpleados();

    @Query(nativeQuery = true, value = "select e.* from employees e inner join jobs j on e.job_id = j.job_id inner join departments d on e.department_id = d.department_id inner join locations l on d.location_id = l.location_id where Lower(e.first_name) like ?1 or Lower(e.last_name) like ?1 or Lower(j.job_title) like ?1 or Lower(l.city) like ?1 order by e.employee_id")
    List<Employee> listarEmpleadosBusqueda(String busqueda);

    @Query(nativeQuery = true, value = "update employees set department_id=?1, job_id=?2 where employee_id=?3")
    void editarEmpleados(int idDepartamento, int idJob, int idEmployee);
}
