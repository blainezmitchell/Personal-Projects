package com.example.demo.subject;

import com.example.demo.teacher.Teacher;

import javax.persistence.*;

@Entity
@Table
public class Subject {

    @Id
    @SequenceGenerator(
            name = "subject_sequence",
            sequenceName = "subject_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subject_sequence"
    )
    private Long subjectId;
    private String subjectName;

    @OneToOne
    private Teacher subjectTeacher;

    public Subject() {
    }

    public Subject(Long subjectId, String subjectName, Teacher subjectTeacher) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectTeacher = subjectTeacher;
    }

    public Subject(String subjectName, Teacher subjectTeacher) {
        this.subjectName = subjectName;
        this.subjectTeacher = subjectTeacher;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Teacher getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(Teacher subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectTeacher=" + subjectTeacher +
                '}';
    }
}
