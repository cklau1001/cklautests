package io.cklau1001.circuitbreaker1.controller;

import io.cklau1001.circuitbreaker1.services.StudentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.cklau1001.circuitbreaker1.model.CustomErrorResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/student")
@CrossOrigin(origins = "*", allowedHeaders = "Access-Control-Allow-Headers", exposedHeaders = "Allow-Control-Allow-Origin")
@ApiResponses({
        @ApiResponse( responseCode = "404",
                description = "Unable to find the student",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
        ),
        @ApiResponse( responseCode = "500", description = "Something goes wrong",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
        )
})
@Tag(name = "Student controller", description = "A controller to manage students")
public class StudentController {

    final private StudentService studentService;

    @GetMapping("/heartbeat")
    public String heartbeat() {
        return "I am alive";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") String id) {

        String str = studentService.getStudent(id);

        return ResponseEntity.ok(str);

    }

}
