package io.cklau1001.circuitbreaker1.services;

import io.cklau1001.circuitbreaker1.feign.StudentRestClient;
import io.cklau1001.circuitbreaker1.model.Student;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class StudentService {

    private final StudentRestClient studentRestClient;

    // @CircuitBreaker(name = "studentService", fallbackMethod = "fallbackMethod")
    // @CircuitBreaker(name = "studentService")
    @Retry(name = "studentRetryService", fallbackMethod = "fallbackWithEx")
    public String getStudent(String id) {

        /*
        If feign has connection issue, it will throw an exception.
        We can try to catch it using a try-block before throwing that exception out
        @ControllerAdvice will finally catch the exception and return the right error response

        fallbackMethod
         */
        try {
            String heartbeat = studentRestClient.getHeartBeat();
            Student s = new Student(id, "Peter", "A");
            return s.toString();
        } catch (Exception e) {
            log.error("[getStudent]: in exception, e=" + e.getMessage());
            throw e;
        }

    }

    public String fallbackMethod(String id, Throwable throwable) {

        String mesg = "Student heartbeat is down due to " + throwable.getMessage();
        log.info("[fallbackMethod]: " + mesg);

        //CallNotPermittedException
        //throw new CallNotPermittedException();

        return "Student heartbeat is down due to " + throwable.getMessage();

    }

    /*
    fallback method must follow the signature of the normal method, including Throwable as another parameter
     */
    public String fallbackWithEx(String id, Throwable t) throws Exception {
        log.error("[fallbackWithEx]: entered" );
        throw (Exception) t;
    }
}
