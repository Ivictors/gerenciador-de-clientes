package br.com.victor.customermanagement.service;

import br.com.victor.customermanagement.dto.CustomerDTO;
import br.com.victor.customermanagement.exceptions.ResourceNotFoundException;
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
import static org.assertj.core.api.Assertions.*;

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
        when(repository.findByEmail(customerDTO.email())).thenReturn(Optional.empty());
       when(repository.save(any(Customer.class))).thenReturn(customer);

        Customer saveCustomer = customerService.save(customerDTO);

        assertNotNull(saveCustomer);
        assertEquals(customer.getEmail(), saveCustomer.getEmail());
        assertEquals(customer.getName(), saveCustomer.getName());

        verify(repository, times(1)).findByEmail(customerDTO.email());
        verify(repository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should return a customer by email")
    void findByEmail() {
        when(repository.findByEmail("victor@test.com")).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findByEmail("victor@test.com");

        assertTrue(result.isPresent());
        assertEquals("victor@test.com", result.get().getEmail());
    }

    @Test
    @DisplayName("Should return every customers")
    void findByAll() {
        when(repository.findAll()).thenReturn(List.of(customer));

        List<Customer> byAll = customerService.findByAll();

        assertFalse(byAll.isEmpty());
        assertEquals(1, byAll.size());
    }

    @Test
    @DisplayName("Should handler exception if not found customer")
    void updateCustomer() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.updateCustomer(id, customerDTO);
        });

        assertTrue(resourceNotFoundException.getMessage().contains("Customer not found"));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should deleted a customer with sucess")
    void deleteById() {
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        customerService.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should throw exception when email already registered")
    void save_EmailAlreadyExists() {
        when(repository.findByEmail(customerDTO.email())).thenReturn(Optional.of(customer));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.save(customerDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("E-mail already registered");
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should update customer successfully")
    void updateCustomer_Success() {
        Long id = 1L;
        CustomerDTO updateDTO = new CustomerDTO("Victor", "Victor@email.com", 30);

        when(repository.findById(id)).thenReturn(Optional.of(customer));
        when(repository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer updated = invocation.getArgument(0);
            return updated;
        });

        Customer result = customerService.updateCustomer(id, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Victor");
        assertThat(result.getEmail()).isEqualTo("Victor@email.com");
        assertThat(result.getAge()).isEqualTo(30);

        verify(repository).findById(id);
        verify(repository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent customer")
    void deleteById_NotFound() {
        Long id = 999L;
        when(repository.existsById(id)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.deleteById(id);
        });

        assertThat(exception.getMessage()).contains("Customer not found");
        verify(repository, never()).deleteById(id);
    }
}