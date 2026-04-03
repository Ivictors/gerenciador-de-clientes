package br.com.victor.customermanagement.repository;

import br.com.victor.customermanagement.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CustomerRepositoryTest {

    private final ICustomerRepository repository;

    private Customer customer;

    CustomerRepositoryTest(ICustomerRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("Victor");
        customer.setEmail("victor@test.com");
        customer.setAge(25);
    }

    @Test
    @DisplayName("Should save customer successfully")
    void save_Success() {
        Customer saved = repository.save(customer);

        assertThat(saved).isNotNull();
        assertThat(saved.getId_Customer()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Victor");
        assertThat(saved.getEmail()).isEqualTo("victor@test.com");
    }

    @Test
    @DisplayName("Should find customer by email")
    void findByEmail_Found() {
        repository.save(customer);

        Optional<Customer> found = repository.findByEmail("victor@test.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("victor@test.com");
    }

    @Test
    @DisplayName("Should return empty when email not found")
    void findByEmail_NotFound() {
        Optional<Customer> found = repository.findByEmail("nonexistent@email.com");

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find all customers")
    void findAll_Success() {
        Customer customer2 = new Customer();
        customer2.setName("Maria");
        customer2.setEmail("maria@test.com");
        customer2.setAge(30);

        repository.save(customer);
        repository.save(customer2);

        List<Customer> customers = repository.findAll();

        assertThat(customers).hasSize(2);
    }

    @Test
    @DisplayName("Should delete customer by id")
    void deleteById_Success() {
        Customer saved = repository.save(customer);
        Long id = saved.getId_Customer();

        repository.deleteById(id);

        Optional<Customer> deleted = repository.findById(id);
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("Should check if customer exists by id")
    void existsById_True() {
        Customer saved = repository.save(customer);

        boolean exists = repository.existsById(saved.getId_Customer());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should check if customer does not exist by id")
    void existsById_False() {
        boolean exists = repository.existsById(999L);

        assertThat(exists).isFalse();
    }
}