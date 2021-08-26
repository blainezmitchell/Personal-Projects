package com.example.demo.teacher;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getTeachers() {
        return teacherService.getTeachers();
    }

    @PostMapping
    public void postTeacher(@RequestBody Teacher teacher) {
        teacherService.postTeacher(teacher);
    }

    @DeleteMapping(path = "{teacherId}")
    public void deleteTeacher(@PathVariable("teacherId") Long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }

    @PutMapping(path = "{teacherId}")
    public void putTeacher(@PathVariable("teacherId") Long teacherId, @RequestParam String teacherName) {
        teacherService.putTeacher(teacherId, teacherName);
    }
}
