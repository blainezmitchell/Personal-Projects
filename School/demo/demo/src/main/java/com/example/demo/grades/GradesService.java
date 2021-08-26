package com.example.demo.grades;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class GradesService {

    private final GradesRepository gradesRepository;

    public GradesService(GradesRepository gradesRepository) {
        this.gradesRepository = gradesRepository;
    }

    public List<Grades> getGrades() {
        return gradesRepository.findAll();
    }

    public void postGrades(Grades grades) {
        if (gradesRepository.existsById(grades.getGradesId())) {
            throw new IllegalStateException("grades with Id " + grades.getGradesId() + " already exist");
        }
        gradesRepository.save(grades);
    }

    public void deleteGrades(Long gradesId) {
        if (!gradesRepository.existsById(gradesId)) {
            throw new IllegalStateException("grades with Id " + gradesId + " do not exist");
        }
        gradesRepository.deleteById(gradesId);
    }

    @Transactional
    public void putGrades(Long gradesId, Integer gradeOne, Integer gradeTwo) {
        Grades grades = gradesRepository.findById(gradesId).orElseThrow(
                () -> new IllegalStateException("grades with Id " + gradesId + " do not exist")
        );

        if ((gradeOne != null) &&
                (gradeOne >= 0) &&
                (gradeOne <= 100) &&
                (!Objects.equals(grades.getGradeOne(), gradeOne))) {
            grades.setGradeOne(gradeOne);
        }

        if ((gradeTwo != null) &&
                (gradeTwo >= 0) &&
                (gradeTwo <= 100) &&
                (!Objects.equals(grades.getGradeTwo(), gradeTwo))) {
            grades.setGradeTwo(gradeTwo);
        }
    }
}
