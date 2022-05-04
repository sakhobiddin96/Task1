package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.payload.Response;
import uz.pdp.task1.repository.AddressRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    public Response addAddress(Address address){
        if (addressRepository.existsByHomeNumberAndStreet(address.getHomeNumber(), address.getStreet())){
            return new Response("This address already exist",false);
        }
        addressRepository.save(address);
        return new Response("Successfully added",true);
    }
    public List<Address> getAddress(){
        return addressRepository.findAll();
    }
    public Response editAddress(@PathVariable Integer id, @RequestBody Address address){
        Optional<Address> byId = addressRepository.findById(id);
        if (byId.isPresent()){
            if (addressRepository.existsByHomeNumberAndStreet(address.getHomeNumber(), address.getStreet())){
                return new Response("This address already exist",false);
            }
            Address address1 = byId.get();
            address1.setHomeNumber(address.getHomeNumber());
            address1.setStreet(address.getStreet());
            addressRepository.save(address1);
            return new Response("Address edited",true);
        }
        return new Response("Address not found",false);
    }
    public Response deleteAddress(@PathVariable Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            addressRepository.deleteById(id);
            return new Response("Address deleted",true);
        }
        return new Response("Address not found",false);
    }
}
