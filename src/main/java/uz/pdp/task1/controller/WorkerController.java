package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Worker;
import uz.pdp.task1.payload.Response;
import uz.pdp.task1.payload.WorkerDto;
import uz.pdp.task1.service.WorkerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;
    @GetMapping
    public ResponseEntity<?> getWorker(){
        List<Worker> workerList = workerService.getWorkers();
        if (workerList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Worker list is empty");
        }
        return ResponseEntity.ok(workerList);
    }
    @PostMapping()
    public ResponseEntity<Response> addWorker( @RequestBody WorkerDto workerDto){
        Response response = workerService.addWorker(workerDto);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> editWorker(@RequestBody WorkerDto workerDto,@PathVariable Integer id){
        Response response = workerService.editWorker(id, workerDto);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteWorker(@PathVariable Integer id){
        Response response = workerService.deleteWorker(id);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }
}
