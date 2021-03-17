package fms.webControllers;

import fms.model.FinanceManagementSystem;
import fms.repositories.FmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/fms")
public class FmsController {

    @Autowired
    FmsRepository repository;

    @GetMapping("/all")
    public @ResponseBody Iterable<FinanceManagementSystem> getAll() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    public @ResponseBody Object getById(@PathVariable("id") int id) {
        Optional<FinanceManagementSystem> item = repository.findById(id);
        if (!item.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return item.get();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable int id) {
        Optional<FinanceManagementSystem> item = repository.findById(id);
        if (item.isPresent()) {
            repository.delete(item.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
