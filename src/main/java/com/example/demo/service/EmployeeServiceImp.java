package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.exceptions.EmployeeNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final SimpleMapper mapper;

    public EmployeeServiceImp(EmployeeRepository employeeRepository, SimpleMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapper.dtoToEntity(employeeDTO);
        Employee employeeSaved = employeeRepository.save(employee);
        return mapper.entityToDTO(employeeSaved);
    }

    @Override
    public EmployeeDTO getEmployee(Long id) {
        return mapper.entityToDTO(getEmployeeIfExistOrTrowException(id));
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = getEmployeeIfExistOrTrowException(id);
            employee.setName(employeeDTO.getName());
            employee.setDepartment(employee.getDepartment());
            employee.setSalary(employee.getSalary());
            Employee updatedEmployee = employeeRepository.save(employee);
            return mapper.entityToDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> getAllEmployee(Pageable pageable) {
        List<Employee> all =  employeeRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "salary"))
        )).getContent();

        return all.stream().map(mapper::entityToDTO).toList();
    }

    private Employee getEmployeeIfExistOrTrowException(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()){
            return employeeOptional.get();
        }else {
            throw new EmployeeNotFoundException(String.format("Employee with id %s was not found", id));
        }
    }
}
