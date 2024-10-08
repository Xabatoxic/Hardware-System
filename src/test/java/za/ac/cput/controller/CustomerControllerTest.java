package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Customer;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.ContactFactory;
import za.ac.cput.factory.CustomerFactory;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/ecommerce/customer";
    private static Contact contact;
    private static Address address;
    private static Customer customer;
    private static Customer savedCustomer;

    @BeforeAll
    static void setUp() {
        contact = ContactFactory.createContact("rupert@gmail.com", "0840841616");
        address = AddressFactory.createAddress("32", "", "", "", "", "Moses", "Paarl", "Western-Cape", "2285");
        customer = CustomerFactory.createCustomer2("rupert@gmail.com", "soon","rupert", "charles", contact, "123456", "Customer", Collections.singletonList(address));
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Customer> customerResponseEntity = restTemplate.postForEntity(url, customer, Customer.class);

        assertNotNull(customerResponseEntity);
        assertNotNull(customerResponseEntity.getBody());

        savedCustomer = customerResponseEntity.getBody();
        customer = Customer.builder()
                .userId(savedCustomer.getUserId())
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .contact(savedCustomer.getContact())
                .password(savedCustomer.getPassword())
                .role(savedCustomer.getRole())
                .addresses(savedCustomer.getAddresses())
                .build();

        System.out.println("Saved Customer : " + customer);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = get_URL("/read/" + savedCustomer.getUserId());
        ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);

        assertNotNull(response.getBody());
        System.out.println("READ: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Customer updatedCustomer = savedCustomer.toBuilder().firstName("RupertUpdated").build();
        String url = BASE_URL + "/update";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Customer> request = new HttpEntity<>(updatedCustomer, headers);

        ResponseEntity<Customer> response = restTemplate.exchange(url, HttpMethod.PUT, request, Customer.class);
        assertNotNull(response.getBody());
        assertEquals("RupertUpdated", response.getBody().getFirstName());
        System.out.println("Updated Customer: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_delete() {
        String url = get_URL("/delete/" + savedCustomer.getUserId());
        restTemplate.delete(url);

        // Check if the customer has been deleted
        ResponseEntity<Customer> response = restTemplate.getForEntity(get_URL("/read/" + savedCustomer.getUserId()), Customer.class);
        assertNull(response.getBody());
        System.out.println("Customer " + savedCustomer.getUserId() + " successfully deleted!!!");
    }

    @Test
    @Order(5)
    void e_getAll() {
        String url = get_URL("/getAll");
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
        System.out.println("All Customers : ");
        System.out.println(response.getBody());
    }

    private String get_URL(String url) {
        return BASE_URL + url;
    }
}
