package com.fileupload.emsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {//the class transfers data from cleient to server
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
