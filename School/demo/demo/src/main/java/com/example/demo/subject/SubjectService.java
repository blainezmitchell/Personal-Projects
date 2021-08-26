package com.example.demo.subject;

import com.example.demo.teacher.Teacher;
import com.example.demo.teacher.TeacherRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    public SubjectService(SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    public void postSubject(Subject subject) {
        if (subjectRepository.existsById(subject.getSubjectId())) {
            throw new IllegalStateException("subject with Id " + subject.getSubjectId() + " already exists");
        }
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new IllegalStateException("subject with Id " + subjectId + " does not exist");
        }
        subjectRepository.deleteById(subjectId);
    }

    @Transactional
    public void putSubject(Long subjectId, String subjectName) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new IllegalStateException("subject with Id " + subjectId + " does not exist")
        );

        if ((subjectName != null) &&
                (subjectName.length() > 0) &&
                (!Objects.equals(subject.getSubjectName(), subjectName))) {
            subject.setSubjectName(subjectName);
        }
    }

    public void addTeacher(Long subjectId, Long teacherId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new IllegalStateException("subject with Id " + subjectId + " does not exist")
        );
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new IllegalStateException("teacher with Id " + teacherId + " does not exist")
        );

        subject.setSubjectTeacher(teacher);
    }
}
