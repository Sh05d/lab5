package org.example.studentlab5.Controller;

import org.example.studentlab5.ApiResponse.ApiResponse;
import org.example.studentlab5.Model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    ArrayList<Student> students = new ArrayList<>();

    @PostMapping("/add-student")
    public ApiResponse addStudent(@RequestBody Student newStudent){
        for(Student student: students){
            if(student.getId().equals(newStudent.getId())){
                return new ApiResponse("Student exist");
            }
        }
        students.add(newStudent);
        return new ApiResponse("Student added successfully");
    }

    @GetMapping("/display-students")
    public ArrayList<Student> displayStudents(){
        return students;
    }

    @PutMapping("/update-sudent/{id}")
    public ApiResponse updateStudent(@PathVariable String id ,@RequestBody Student student){
        for(int i=0; i< students.size(); i++){
            if(students.get(i).getId().equals(id)){
                student.setId(id); //to make sure id not change;
                students.set(i, student);
                return new ApiResponse("Student updated successfully");
            }
        }
        return new ApiResponse("Student not found");
    }

    @DeleteMapping("/delete-student/{id}")
    public ApiResponse deleteStudent(@PathVariable String id){
        for(int i=0; i< students.size(); i++){
            if(students.get(i).getId().equals(id)){
                students.remove(i);
                return new ApiResponse("Student deleted successfully");
            }
        }
        return new ApiResponse("Student not found");
    }


    @GetMapping("classify-student")
    public String classifyStudents(){
        ArrayList<Student> firstHonor = new ArrayList<>();
        ArrayList<Student> secondHonor = new ArrayList<>();

        for(Student student: students){
            if(student.getGpa() >= 4.75){
                firstHonor.add(student);
            }
            else if(student.getGpa() >= 4.5){
                secondHonor.add(student);
            }
        }
        return "First honors: \n" +firstHonor+"\n Second honor:\n"+secondHonor;
    }

    @GetMapping("/above-average")
    public ArrayList<Student> displayGreaterThanAverage(){
        ArrayList<Student> aboveAverage = new ArrayList<>();

        double average = 0, sumOfGpa =0;
        int count =0;
        for(Student student: students){
            count++;
            sumOfGpa += student.getGpa();
        }

        average = sumOfGpa/count;

        for(Student student : students){
            if(student.getGpa() > average)
                aboveAverage.add(student);
        }

        return aboveAverage;
    }



}
