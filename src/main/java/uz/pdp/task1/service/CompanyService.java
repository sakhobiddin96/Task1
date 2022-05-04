package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.payload.CompanyDto;
import uz.pdp.task1.payload.Response;
import uz.pdp.task1.repository.AddressRepository;
import uz.pdp.task1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service

public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;
    public Response  addCompany(@RequestBody CompanyDto companyDto){
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (optionalAddress.isPresent()){
            Address address = optionalAddress.get();
            Company company=new Company();
            company.setAddress(address);
            company.setCorpName(companyDto.getCorpName());
            company.setDirectorName(companyDto.getDirectorName());
            companyRepository.save(company);
            return new Response("Company added",true);

        }
        return new Response("Address not found to add",false);
    }
    public List<Company> getCompany(){
        return companyRepository.findAll();
    }
    public Response editCompany(@RequestBody CompanyDto companyDto, @PathVariable Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()){
            Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
            if (optionalAddress.isPresent()){
                Address address = optionalAddress.get();
                Company company = optionalCompany.get();
                company.setDirectorName(companyDto.getDirectorName());
                company.setAddress(address);
                company.setCorpName(companyDto.getCorpName());
                companyRepository.save(company);
                return new Response("Company edited",true);
            }
            return new Response("Address not found to add",false);
        }
        return new Response("Company not found",false);
    }
    public Response deleteCompany(@PathVariable Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()){
            companyRepository.deleteById(id);
            return new Response("Company deleted",true);
        }
        return new Response("Company not found",false);
    }
}
