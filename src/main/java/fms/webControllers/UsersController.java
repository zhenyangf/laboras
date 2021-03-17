package fms.webControllers;

import fms.model.FinanceManagementSystem;
import fms.model.User;
import fms.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersRepository repository;

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAll() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    public @ResponseBody Object getById(@PathVariable("id") int id) {
        Optional<User> item = repository.findById(id);
        if (!item.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return item.get();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable int id) {
        Optional<User> item = repository.findById(id);
        if (item.isPresent()) {
            repository.delete(item.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object login(@RequestBody UserData userData) {
        List<User> users = repository.findByCredentials(userData.username, userData.password);

        if (users.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return users.get(0);
    }

    static private class UserData {
        public String username;
        public String password;
    }
}
