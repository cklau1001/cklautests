package io.cklau1001.circuitbreaker1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// @FeignClient(name = "studentRestClient", url="http://localhost:8083")
@FeignClient(name = "studentClient", url="http://localhost:8161")
public interface StudentRestClient {

    //@GetMapping("/api/v1/student/heartbeat")
    @GetMapping("/index1.html")
    String getHeartBeat();

}
