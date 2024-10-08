// CustomerServiceTest.java
package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Customer;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.ContactFactory;
import za.ac.cput.factory.CustomerFactory;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;
    private static Contact contact = ContactFactory.createContact("john1@gmail.com", "02189456123");
    private static Address address = AddressFactory.createAddress("21", "", "", "",
            "", "Moses", "Capetown", "Western Cape", "7646");

    // Ensure the email address is valid
    private static Customer customer = CustomerFactory.createCustomer2("john1@gmail.com","soon", "John", "Wick", contact, "123456", "Customer", Collections.singletonList(address));

    @Test
    @Order(1)
    void a_create() {
        Customer created = customerService.create(customer);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    @Order(2)
    void b_read() {
        Customer readCustomer = customerService.read(customer.getUserId());
        assertNotNull(readCustomer);
        System.out.println(readCustomer);
    }

    @Test
    @Order(3)
    void c_update() {
        Customer newCustomer = customer.copy().toBuilder().firstName("Johnathan").build();
        Customer updated = customerService.update(newCustomer);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Disabled
    @Order(4)
    void d_delete() {
        customerService.delete(customer.getUserId());
        System.out.println("Customer Deleted Where User ID = " + customer.getUserId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println(customerService.getAll());
    }
}