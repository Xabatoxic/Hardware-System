package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.factory.AddressFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    private static Address address;

    @BeforeAll
    static void setUp() {
        address = AddressFactory.createAddress(
                "123",
                "Unit 45",
                "Complex 9",
                "Sunset Villas",
                "Apt 12",
                "Sunset Boulevard",
                "Cape Town",
                "Western Cape",
                "8000"
        );
    }

    @Test
    @Order(1)
    void a_create() {
        Address created = addressService.create(address);
        assertNotNull(created, "The created address should not be null");
        assertEquals(address.getStreetNumber(), created.getStreetNumber(), "The street number should match");
        assertEquals(address.getCity(), created.getCity(), "The city should match");
        System.out.println("Created Address: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Address read = addressService.read(address.getStreetNumber());
        assertNotNull(read, "The read address should not be null");
        assertEquals(address.getStreetNumber(), read.getStreetNumber(), "The street number should match");
        System.out.println("Read Address: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Address updated = new Address.Builder()
                .setStreetNumber(address.getStreetNumber())
                .setCity("Johannesburg") // Change city for update
                .setState(address.getState())
                .setPostalCode(address.getPostalCode())
                .build();

        Address result = addressService.update(updated);
        assertNotNull(result, "The updated address should not be null");
        assertEquals(updated.getCity(), result.getCity(), "The updated city should match");
        System.out.println("Updated Address: " + result);
    }

    @Test
    @Order(4)
    @Disabled("Test is disabled until delete operation is required")
    void d_delete() {
        addressService.delete(address.getStreetNumber());
        Address deleted = addressService.read(address.getStreetNumber());
        assertNull(deleted, "The address should be null after deletion");
        System.out.println("Deleted Address: " + address.getStreetNumber());
    }

    @Test
    @Order(5)
    void e_getAll() {
        Set<Address> addresses = addressService.getAll();
        assertFalse(addresses.isEmpty(), "The set of addresses should not be empty");
        System.out.println("All Addresses: " + addresses);
    }
}