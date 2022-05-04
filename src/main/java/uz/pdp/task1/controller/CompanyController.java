package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.payload.CompanyDto;
import uz.pdp.task1.payload.Response;
import uz.pdp.task1.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;
    @PostMapping
    public ResponseEntity<Response> addCompany(@RequestBody CompanyDto companyDto){
        Response response = companyService.addCompany(companyDto);
        if (response.isSuccess())
            return  ResponseEntity.ok(response);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @GetMapping
    public ResponseEntity<?> getCompany(){
        List<Company> companyList = companyService.getCompany();
        if (companyList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(companyList);
        else
            return ResponseEntity.ok(companyList);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> editCompany(@PathVariable Integer id,@RequestBody CompanyDto companyDto){
        Response response = companyService.editCompany(companyDto, id);
        if (response.isSuccess())
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCompany(@PathVariable Integer id){
        Response response = companyService.deleteCompany(id);
        if (response.isSuccess())
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
