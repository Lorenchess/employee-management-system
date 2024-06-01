package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImpTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    SimpleMapper mapper;

    @InjectMocks
    private EmployeeServiceImp employeeService;

    private Employee employee;


    private EmployeeDTO employeeDTO;

    private EmployeeDTO mappedEmployeeDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .name("Ramon Lorente")
                .salary(100.000)
                .department("Engineering")
                .build();
        employeeDTO = EmployeeDTO.builder()
                .id(null)
                .name("Ramon Lorente")
                .department("Engineering")
                .salary(100.000)
                .build();
        mappedEmployeeDTO = EmployeeDTO.builder()
                .id(1L)
                .name("Ramon Lorente")
                .department("Engineering")
                .salary(100.000)
                .build();
    }

    @Test
    void createEmployee() {

        //given
        when(mapper.dtoToEntity(any(EmployeeDTO.class))).thenReturn(employee);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        when(mapper.entityToDTO(any(Employee.class))).thenReturn(mappedEmployeeDTO);

        //when
        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        //then
        assertEquals(result.getId(), employee.getId());
    }

    @Test
    void getEmployee() {
        //given
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));

        when(mapper.entityToDTO(any(Employee.class))).thenReturn(mappedEmployeeDTO);


        //when
        EmployeeDTO result = employeeService.getEmployee(employee.getId());

        //then
        assertEquals(result.getId(), employee.getId());

    }

    @Test
    void updateEmployee() {
        //given
        EmployeeDTO newEmployee = EmployeeDTO.builder()
                .id(1L)
                .name("Ramon Lorente")
                .department("Enginering")
                .salary(200.000)
                .build();
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .name("Ramon Lorente")
                .department("Enginering")
                .salary(200.000)
                .build();
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
        when(mapper.entityToDTO(any(Employee.class))).thenReturn(newEmployee);

        //when
        EmployeeDTO result = employeeService.updateEmployee(newEmployee.getId(), newEmployee);

        //then
        assertEquals(result.getSalary(), updatedEmployee.getSalary());

    }

    @Test
    void deleteEmployee() {
        //given
        Long id = 1L;
        doNothing().when(employeeRepository).deleteById(id);

        //when
        employeeService.deleteEmployee(employee.getId());

        //then
        verify(employeeRepository,times(1)).deleteById(id);

    }

    @Test
    void getAllEmployee() {
        //given
        List<Employee> employees = new ArrayList<>(Arrays.asList(employee, employee, employee, employee));

        Pageable pageable = PageRequest.of(0, 10) ;

        Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(employeePage);

        when(mapper.entityToDTO(any(Employee.class))).thenReturn(employeeDTO);

        List<EmployeeDTO> allEmployee = employeeService.getAllEmployee(pageable);

        assertEquals(allEmployee.size(), employees.size());
    }
}