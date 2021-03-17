package fms.webControllers;

import fms.model.Category;
import fms.model.Income;
import fms.repositories.CategoriesRepository;
import fms.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    IncomeRepository repository;

    @GetMapping("/all")
    public @ResponseBody Iterable<Income> getAll() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    public @ResponseBody Object getById(@PathVariable("id") int id) {
        Optional<Income> item = repository.findById(id);
        if (!item.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return item.get();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable int id) {
        Optional<Income> item = repository.findById(id);
        if (item.isPresent()) {
            repository.delete(item.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
