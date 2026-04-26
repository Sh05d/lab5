package org.example.studentlab5.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private String id;
    private String name;
    private int age;
    private String degree;
    private double gpa;
}
