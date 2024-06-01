package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeRequest) {
        EmployeeDTO employee = employeeService.createEmployee(employeeRequest);

        return ResponseEntity.created(withLocation(employee.getId())).body(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable(name = "id") Long employeeId) {
        EmployeeDTO employee = employeeService.getEmployee(employeeId);

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeRequest){
        EmployeeDTO employeeDTO = employeeService.updateEmployee(id, employeeRequest);

        return ResponseEntity.ok(employeeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>>getAllEmployees(Pageable pageable) {
        List<EmployeeDTO> allEmployee = employeeService.getAllEmployee(pageable);
        return ResponseEntity.ok(allEmployee);
    }


    private URI withLocation(Long resourceId) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{resourceId}").buildAndExpand(resourceId).toUri();
        return location;
    }
}
