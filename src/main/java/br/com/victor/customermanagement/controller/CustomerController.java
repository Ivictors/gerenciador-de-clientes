package br.com.victor.customermanagement.controller;

import br.com.victor.customermanagement.dto.CustomerDTO;
import br.com.victor.customermanagement.model.Customer;
import br.com.victor.customermanagement.service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public ResponseEntity<Customer> save(@RequestBody @Valid CustomerDTO customerDto) {
        customerService.save(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerDto));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> findByEmail(@PathVariable(name = "email") String email) {
        return customerService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/show-all")
    public ResponseEntity<List<Customer>> findByAll() {
        List<Customer> customers = customerService.findByAll();

        if (customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Long id) {
        customerService.deleteById(id);
    }
}
