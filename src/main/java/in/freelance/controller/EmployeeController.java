package in.freelance.controller;

import in.freelance.model.Employee;
import in.freelance.repo.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Operation(summary = "Insert employee information")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Employee information inserted",
                    content = {@Content(mediaType = "application/json")}),
    })
    @PostMapping(value="/employee",
            consumes = {"application/xml", "application/json"}
    )
    public ResponseEntity<String> saveEmployeeInfo(@RequestBody Employee employee){
        employeeRepository.save(employee);
        return new ResponseEntity <>("Employee information saved", HttpStatus.CREATED);
    }


    @Operation(summary = "Fetch employee information")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Fetched employee information",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Failed to fetch employee information",
                    content = @Content)
    })
    @GetMapping(value="/allEmployees",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Employee>>  getAllEmployees(){
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK) ;
    }


    @Operation(summary = "Update employee information for given userId")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "updates employee information for given userId",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Failed to update employee information",
                    content = @Content)
    })
    @PatchMapping(value="employee/{empId}",
            consumes = {"application/xml", "application/json"})
    public ResponseEntity <String> updateEmployee(@PathVariable Integer empId, @RequestBody Employee employee){
        employeeRepository.save(employee);
        return new ResponseEntity<>("EMployee information updated", HttpStatus.OK);
    }


    @Operation(summary = "Delete employee information for given userId")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "deletes employee information for given userId"),
            @ApiResponse(responseCode = "404",
                    description = "Failed to delete employee information")
    })
    @DeleteMapping("employee/{empId}")
    public ResponseEntity <String> deleteEmployee(@PathVariable Integer empId){
        employeeRepository.deleteById(empId);
        return new ResponseEntity<>("Employee deleted", HttpStatus.OK);
    }




}
