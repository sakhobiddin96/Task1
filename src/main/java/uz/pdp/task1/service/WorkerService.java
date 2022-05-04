package uz.pdp.task1.service;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.entity.Worker;
import uz.pdp.task1.payload.Response;
import uz.pdp.task1.payload.WorkerDto;
import uz.pdp.task1.repository.AddressRepository;
import uz.pdp.task1.repository.DepartmentRepository;
import uz.pdp.task1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;
    public Response addWorker( WorkerDto workerDto){
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isPresent()){
            Department department = optionalDepartment.get();
            Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
            if (optionalAddress.isPresent()){
                Address address = optionalAddress.get();
                Worker worker=new Worker();
                worker.setDepartment(department);
                worker.setAddress(address);
                worker.setName(workerDto.getName());
                worker.setPhoneNumber(workerDto.getPhoneNumber());
                workerRepository.save(worker);
                return new Response("Worker added",true);
            }
            return new Response("Address not found to add",false);

        }
        return new Response("Department not found to add",false);
    }
    public List<Worker> getWorkers(){
        return workerRepository.findAll();
    }
    public Response editWorker(Integer id,WorkerDto workerDto){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()){
            Worker worker = optionalWorker.get();
            Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
            if (optionalAddress.isPresent()){
                Address address = optionalAddress.get();
                Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
                if (optionalDepartment.isPresent()){
                    Department department = optionalDepartment.get();
                    worker.setPhoneNumber(workerDto.getPhoneNumber());
                    worker.setAddress(address);
                    worker.setDepartment(department);
                    worker.setName(workerDto.getName());
                    workerRepository.save(worker);
                    return new Response("Worker edited",true);
                }
                return new Response("Department not found",false);
            }
            return new Response("Address not found",false);

        }
        return new Response("Worker not found",false);
    }
    public Response deleteWorker(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()){
            workerRepository.deleteById(id);
            return new Response("Worker deleted",true);
        }
        return new Response("Worker not found",false);
    }
}
