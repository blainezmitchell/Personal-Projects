package com.example.demo.teacher;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public void postTeacher(Teacher teacher) {
        if (teacherRepository.existsById(teacher.getTeacherId())) {
            throw new IllegalStateException("teacher with Id " + teacher.getTeacherId() + " already exists");
        }
        teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new IllegalStateException("teacher with Id " + teacherId + " does not exist");
        }
        teacherRepository.deleteById(teacherId);
    }

    @Transactional
    public void putTeacher(Long teacherId, String teacherName) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new IllegalStateException("teacher with Id " + teacherId + " does not exist")
        );

        if ((teacherName != null) &&
                (teacherName.length() > 0) &&
                (!Objects.equals(teacher.getTeacherName(), teacherName))) {
            teacher.setTeacherName(teacherName);
        }
    }
}
