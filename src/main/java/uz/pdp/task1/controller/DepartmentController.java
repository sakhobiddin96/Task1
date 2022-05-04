package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payload.DepartmentDto;
import uz.pdp.task1.payload.Response;
import uz.pdp.task1.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @GetMapping
    public ResponseEntity<?> getDepartment(){
        List<Department> departmentList = departmentService.getDepartment();
        if (departmentList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Department list is empty");
        else
            return ResponseEntity.ok(departmentList);

    }
    @PostMapping
    public ResponseEntity<Response> addDepartment(@RequestBody DepartmentDto departmentDto){
        Response response = departmentService.addDepartment(departmentDto);
        if (response.isSuccess())
            return ResponseEntity.ok(response);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> editDepartment(@PathVariable Integer id,@RequestBody DepartmentDto departmentDto){
        Response response = departmentService.editDepartment(departmentDto, id);
        if (response.isSuccess())
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteDepartment(@PathVariable Integer id){
        Response response = departmentService.deleteDepartment(id);
        if (response.isSuccess())
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }
}
