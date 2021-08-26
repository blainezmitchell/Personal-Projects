package com.example.demo.student;

import com.example.demo.grades.Grades;
import com.example.demo.subject.Subject;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long studentId;
    private String studentName;

    @OneToMany
    private Map<Subject, Grades> subjectGradesMap = new HashMap<>();

    public Student() {
    }

    public Student(Long studentId, String studentName, Map<Subject, Grades> subjectGradesMap) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjectGradesMap = subjectGradesMap;
    }

    public Student(String studentName, Map<Subject, Grades> subjectGradesMap) {
        this.studentName = studentName;
        this.subjectGradesMap = subjectGradesMap;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Map<Subject, Grades> getSubjectGradesMap() {
        return subjectGradesMap;
    }

    public void setSubjectGradesMap(Map<Subject, Grades> subjectGradesMap) {
        this.subjectGradesMap = subjectGradesMap;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", subjectGradesMap=" + subjectGradesMap +
                '}';
    }
}
