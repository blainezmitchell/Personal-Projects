package com.example.demo.grades;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/grades")
public class GradesController {

    private final GradesService gradesService;

    public GradesController(GradesService gradesService) {
        this.gradesService = gradesService;
    }

    @GetMapping
    public List<Grades> getGrades() {
        return gradesService.getGrades();
    }

    @PostMapping
    public void postGrades(@RequestBody Grades grades) {
        gradesService.postGrades(grades);
    }

    @DeleteMapping(path = "{gradesId}")
    public void deleteGrades(@PathVariable("gradesId") Long gradesId) {
        gradesService.deleteGrades(gradesId);
    }

    @PutMapping(path = "{gradesId}")
    public void putGrades(@PathVariable("gradesId") Long gradesId,
                          @RequestParam(required = false) Integer gradeOne,
                          @RequestParam(required = false) Integer gradeTwo) {
        gradesService.putGrades(gradesId, gradeOne, gradeTwo);
    }
}
