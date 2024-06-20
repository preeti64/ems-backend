package com.fileupload.emsbackend.service.impl;

import com.fileupload.emsbackend.dto.EmployeeDto;
import com.fileupload.emsbackend.entity.Employee;
import com.fileupload.emsbackend.exception.ResourceNotFoundException;
import com.fileupload.emsbackend.mapper.EmployeeMapper;
import com.fileupload.emsbackend.repository.EmployeeRepository;
import com.fileupload.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        //we would save EmployeeJpa instance in databse
        //in line 20 , we have used mapper object to convert employeeDto to employee as we would save Employee in databse
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmp = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmp);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId){
        Employee employee;
        try {
            employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("The employee id does not exist : " + employeeId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> list = employeeRepository.findAll();
        return list.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee;
        try {
            employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("The employee id does not exist : " + employeeId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee;
        try {
            employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("The employee id does not exist : " + employeeId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        employeeRepository.deleteById(employeeId);
    }
}
