package com.example.demosecurity.rest;


import com.example.demosecurity.entity.Student;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class FullapirestController {
    List<Student> studentList;

    @PostConstruct
    public void loadData() {
        studentList = new ArrayList<>();

        studentList.add(new Student(1, "Jorge", "Miralles"));
        studentList.add(new Student(2, "Pedro", "Perez"));
        studentList.add(new Student(3, "Enrique", "Giol"));

    }

    @GetMapping("/students")
    public List<Student> listStudents() {
        return studentList;
    }

    @GetMapping("/hola")
    public String hola() {
        return "hola";
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        for(Student student: this.studentList){
            if(student.getId() == studentId){
                return student;
            }
        }

        return null;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student theStudent){

        studentList.add(theStudent);

        return theStudent;
    }

    @PutMapping("/students")
    public Student updateStudent(@RequestBody Student theStudent){

        for (Student student : studentList) {
            if(student.getId() == theStudent.getId()){
                student.setFirstName(theStudent.getFirstName());
                student.setLastName(theStudent.getLastName());
            }
        }

        return theStudent;
    }

    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable int studentId){

        this.studentList.removeIf(s -> s.getId() == studentId);

        return "Borrado el estudiante: " + studentId;
    }

}