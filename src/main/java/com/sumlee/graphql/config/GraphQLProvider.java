package com.sumlee.graphql.config;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sumlee.graphql.repository.SchoolRepository;
import com.sumlee.graphql.service.SchoolService;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolService schoolService;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    public ExecutionResult executeQuery(Map<String, Object> request) {
        return graphQL.execute(ExecutionInput.newExecutionInput()
                .query((String) request.get("query"))
                .variables((Map) request.get("variables"))
                .build());
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("schools", env -> schoolRepository.findAll()))
                .type(newTypeWiring("Query")
                        .dataFetcher("school", env -> schoolRepository.findById(env.getArgument("id"))))
                .type(newTypeWiring("Query")
                        .dataFetcher("schoolByParams", env -> schoolRepository.findById(env.getArgument("id"))))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createSchool", env -> schoolService.createSchool(env.getArgument("name"), env.getArgument("type"), env.getArgument("location"))))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("deleteSchool", env -> schoolService.deleteSchoolById(env.getArgument("id"))))
                .build();
    }
}
