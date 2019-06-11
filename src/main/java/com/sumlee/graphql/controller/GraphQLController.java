package com.sumlee.graphql.controller;

import com.sumlee.graphql.config.GraphQLProvider;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("graphql")
public class GraphQLController {

    @Autowired
    private GraphQLProvider graphQLProvider;

    @PostMapping
    public ResponseEntity<Map<String, List>> getSchools(@RequestBody Map<String, Object> request) {
        ExecutionResult executionResult = graphQLProvider.executeQuery(request);

        return new ResponseEntity<>(((Map<String, List>)executionResult.getData()), HttpStatus.OK);
    }

}
