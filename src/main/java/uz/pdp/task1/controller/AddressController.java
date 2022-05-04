package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Address;

import uz.pdp.task1.payload.Response;
import uz.pdp.task1.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping
    public ResponseEntity<Response> addAddress(@RequestBody Address address) {
        Response response = addressService.addAddress(address);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAddress() {
        List<Address> address = addressService.getAddress();
        if (!address.isEmpty()) {
            return ResponseEntity.ok(address);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("List is empty");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> editAddress(@RequestBody Address address, @PathVariable Integer id) {
        Response response = addressService.editAddress(id, address);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteAddress(@PathVariable Integer id) {
        Response response = addressService.deleteAddress(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
