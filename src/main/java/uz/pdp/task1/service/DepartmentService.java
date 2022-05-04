package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payload.DepartmentDto;
import uz.pdp.task1.payload.Response;
import uz.pdp.task1.repository.CompanyRepository;
import uz.pdp.task1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    public Response addDepartment(@RequestBody DepartmentDto departmentDto) {
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            Department department = new Department();
            department.setCompany(company);
            department.setName(departmentDto.getName());
            departmentRepository.save(department);
            return new Response("Department added",true);
        }
        return new Response("Company not found to add",false);
    }
    public List<Department> getDepartment(){
        List<Department> all = departmentRepository.findAll();
        return all;
    }
    public Response editDepartment(@RequestBody DepartmentDto departmentDto, @PathVariable Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()){
            Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
            if (optionalCompany.isPresent()){
                Company company = optionalCompany.get();
                Department department = optionalDepartment.get();
                department.setName(departmentDto.getName());
                department.setCompany(company);
                departmentRepository.save(department);
                return new Response("Department edited",true);
            }
            return new Response("Company not found",false);
        }
        return new Response("Department not found",false);
    }
    public Response deleteDepartment(@PathVariable Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()){
            departmentRepository.deleteById(id);
            return new Response("Department deleted",true);
        }
        return new Response("Department not found",false);
    }
}
