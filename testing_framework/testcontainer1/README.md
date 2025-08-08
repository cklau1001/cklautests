# Introduction
This is a simple SpringBoot JPA project that shows how to perform testing using Mockito and TestContainer.
PostgreSQL database is used with the corresponding test container. 
In any cases, both unit and integration testings are important and they are not mutually exclusive.
Unit test cases can give a developer quick run to confirm that a behavior is expected and integration tests can reveal any missing scenarios by using a more real infra-structure.

# Files
Same as all Springboot projects, the testing codes can be found as below:
```requirements
<project_root>/
                test/java/
                    com/example/testcontainer1/
                                   unit/         # unit testing by Mockito
                                   integration/  # integration testing by PostgresSQL testcontainer
                                   
```

