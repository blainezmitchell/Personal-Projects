package com.example.demo.student;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void postStudent(@RequestBody Student student) {
        studentService.postStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void putStudent(@PathVariable("studentId") Long studentId, @RequestParam String studentName) {
        studentService.putStudent(studentId, studentName);
    }

    @PutMapping(path = "addGrades/{studentId}")
    public void addGrades(@PathVariable("studentId") Long studentId,
                          @RequestParam Long subjectId,
                          @RequestParam Long gradesId) {
        studentService.addGrades(studentId, subjectId, gradesId);
    }

    @GetMapping(path = "reportCard/{studentId}")
    public Student reportCard(@PathVariable("studentId") Long studentId) {
        return studentService.reportCard(studentId);
    }
}
