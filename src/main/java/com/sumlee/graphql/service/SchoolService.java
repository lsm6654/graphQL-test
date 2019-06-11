package com.sumlee.graphql.service;

import com.sumlee.graphql.model.School;
import com.sumlee.graphql.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    public School createSchool(String name, String type, String location) {
        return schoolRepository.save(new School(name, type, location));
    }

    public School deleteSchoolById(Long id) {
        School school = schoolRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        schoolRepository.deleteById(id);

        return school;
    }
}
