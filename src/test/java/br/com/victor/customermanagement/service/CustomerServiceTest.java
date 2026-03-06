package br.com.victor.customermanagement.service;

import br.com.victor.customermanagement.dto.CustomerDTO;
import br.com.victor.customermanagement.model.Customer;
import br.com.victor.customermanagement.repository.ICustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private ICustomerRepository repository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId_Customer(1L);
        customer.setName("Victor");
        customer.setEmail("victor@test.com");
        customer.setAge(25);

        customerDTO = new CustomerDTO("Victor", "victor@test.com", 25);
    }

    @Test
    @DisplayName("Should save customer with sucess")
    void save() {
        when(repository.save(any(Customer.class))).thenReturn(customer);

        Customer save = customerService.save(customerDTO);

        assertNotNull(save);
        assertEquals("Victor", save.getName());
        verify(repository, times(1)).save(customer);
    }

    @Test
    @DisplayName("Should return a customer by email")
    void findByEmail() {
        when(repository.findByEmail("victor@test.com")).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findByEmail("victor@test.com");

        assertTrue(result.isPresent());
        assertEquals("victor@test.com",result.get().getEmail());
    }

    @Test
    @DisplayName("Should return list is empty when not found customer")
    void findByAll() {
        when(repository.findAll()).thenReturn(List.of());

        List<Customer> byAll = customerService.findByAll();

        assertTrue(byAll.isEmpty());
    }

    @Test
    @DisplayName("Should handler exception if not found customer")
    void updateCustomer() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomer(id, customerDTO);
        });

        assertTrue(runtimeException.getMessage().contains("Customer not found"));
        verify(repository,never()).save(any());
    }

    @Test
    @DisplayName("Should deleted a customer with sucess")
    void deleteById() {
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        customerService.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }
}