# JAVA-Spring boot-JPA GralphQL TEST

http://localhost:8080/graphiql


## query Example

1. find all schools <br>

{
  allSchools: schools {
    id
    name
    type
    location
    students {
      id
      age
      name
    }
  }
}



2. find school by id <br>

query findSchool($schoolId: Long) {
  school(id: $schoolId) {
    id
    name
    location
    students {
      id
      name
      age
    }
  }
}

- query variables <br>
{
  "schoolId" : 1
}



3. create school <br>

mutation createSchool($name: String, $type: String, $location: String) {
  createSchool(name: $name, type: $type, location: $location) {
    id
    name
    location
    students {
      id
      name
      age
    }
  }
}

- query variables <br>

{
  "name": "test-name-1",
  "type": "test-type-1",
  "location": "test-location-1"
}