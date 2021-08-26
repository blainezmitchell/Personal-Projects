package com.example.demo.student;

import com.example.demo.grades.Grades;
import com.example.demo.grades.GradesRepository;
import com.example.demo.subject.Subject;
import com.example.demo.subject.SubjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final GradesRepository gradesRepository;

    public StudentService(StudentRepository studentRepository,
                          SubjectRepository subjectRepository,
                          GradesRepository gradesRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.gradesRepository = gradesRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void postStudent(Student student) {
        if (studentRepository.existsById(student.getStudentId())) {
            throw new IllegalStateException("student with Id " + student.getStudentId() + " already exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalStateException("student with Id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void putStudent(Long studentId, String studentName) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("student with Id " + studentId + " does not exist")
        );

        if ((studentName != null) &&
                (studentName.length() > 0) &&
                (!Objects.equals(student.getStudentName(), studentName))) {
            student.setStudentName(studentName);
        }
    }

    public void addGrades(Long studentId, Long subjectId, Long gradesId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("student with Id " + studentId + " does not exist")
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new IllegalStateException("subject with Id " + subjectId + " does not exist")
        );
        Grades grades = gradesRepository.findById(gradesId).orElseThrow(
                () -> new IllegalStateException("grades with Id " + gradesId + " do not exist")
        );

        student.addGrades(subject, grades);
    }

    public Student reportCard(Long studentId) {
        return studentRepository.getById(studentId);
    }
}
