# Introduction
This is a simple SpringBoot JPA project that shows how to perform testing using Mockito, TestContainer and MockServer.
PostgreSQL database is used with the corresponding test container.
A typical 3-tier Restful API is used to show how the test cases can be written.
DTO pattern is used to optimize DB query and avoid N+1 query issue by using ORM.

```requirements

incoming request --->  Controller  --> Services --> Repository --> PostgresSQL
                                            |
                                            V
                                external weather API endpoint

```
# Notes
- A docker daemon is needed for running Testcontainer.
- Strictly speaking, a unit test case with Mockito is faster than using Testcontainer. However, Testcontainer can allow to use
  testing data without any complicated mocking.
- MockServer is similar to MockRestServiceServer but the latter needs a spring context.

# Files
Same as all Springboot projects, the testing codes can be found as below:
```requirements
<project_root>/
                test/java/
                    com/example/testcontainer1/
                                   unit/         # unit testing by Mockito
                                   integration/  # integration testing by PostgreSQL testcontainer
                                   
```

