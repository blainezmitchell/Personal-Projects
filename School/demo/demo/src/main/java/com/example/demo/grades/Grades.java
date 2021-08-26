package com.example.demo.grades;

import javax.persistence.*;

@Entity
@Table
public class Grades {

    @Id
    @SequenceGenerator(
            name = "grades_sequence",
            sequenceName = "grades_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "grades_sequence"
    )
    private Long gradesId;
    private Integer gradeOne;
    private Integer gradeTwo;
    @Transient
    private Double gradesAverage;

    public Grades() {
    }

    public Grades(Long gradesId, int gradeOne, int gradeTwo) {
        this.gradesId = gradesId;
        this.gradeOne = gradeOne;
        this.gradeTwo = gradeTwo;;
    }

    public Grades(int gradeOne, int gradeTwo) {
        this.gradeOne = gradeOne;
        this.gradeTwo = gradeTwo;;
    }

    public Long getGradesId() {
        return gradesId;
    }

    public void setGradesId(Long gradesId) {
        this.gradesId = gradesId;
    }

    public int getGradeOne() {
        return gradeOne;
    }

    public void setGradeOne(int gradeOne) {
        this.gradeOne = gradeOne;
    }

    public int getGradeTwo() {
        return gradeTwo;
    }

    public void setGradeTwo(int gradeTwo) {
        this.gradeTwo = gradeTwo;
    }

    public double getGradesAverage() {
        return (double)(gradeOne + gradeTwo) / 2;
    }

    public void setGradesAverage(double gradesAverage) {
        this.gradesAverage = gradesAverage;
    }

    @Override
    public String toString() {
        return "Grades{" +
                "gradesId=" + gradesId +
                ", gradeOne=" + gradeOne +
                ", gradeTwo=" + gradeTwo +
                ", gradesAverage=" + gradesAverage +
                '}';
    }

}
