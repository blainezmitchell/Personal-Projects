package com.example.demo.subject;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getSubjects() {
        return subjectService.getSubjects();
    }

    @PostMapping
    public void postSubject(@RequestBody Subject subject) {
        subjectService.postSubject(subject);
    }

    @DeleteMapping(path = "{subjectId}")
    public void deleteSubject(@PathVariable("subjectId") Long subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    @PutMapping(path = "{subjectId}")
    public void putStudent(@PathVariable("subjectId") Long subjectId, @RequestParam String subjectName) {
        subjectService.putSubject(subjectId, subjectName);
    }

    @PutMapping(path = "addTeacher/{subjectId}")
    public void addTeacher(@PathVariable("subjectId") Long subjectId, @RequestParam Long teacherId) {
        subjectService.addTeacher(subjectId, teacherId);
    }
}
