package br.com.victor.customermanagement.service;

import br.com.victor.customermanagement.dto.CustomerDTO;
import br.com.victor.customermanagement.model.Customer;
import br.com.victor.customermanagement.repository.ICustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer save(CustomerDTO customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setAge(customerDto.age());
        return customerRepository.save(customer);
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> findByAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer updateCustomer(Long id, CustomerDTO customerDto){
       return customerRepository.findById(id).map(customerExists -> {
            customerExists.setName(customerDto.name());
            customerExists.setEmail(customerDto.email());
            customerExists.setAge(customerDto.age());

            return customerRepository.save(customerExists);
    }).orElseThrow(() -> new RuntimeException("Customer not found by id: " + id));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }
}
