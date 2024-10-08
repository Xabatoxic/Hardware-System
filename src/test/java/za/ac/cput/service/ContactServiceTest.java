package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Contact;
import za.ac.cput.factory.ContactFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    private static Contact contact;

    @BeforeAll
    static void setUp() {
        contact = ContactFactory.createContact(
                "john.doe@example.com",
                "0123456789"
        );
    }

    @Test
    @Order(1)
    void a_create() {
        Contact created = contactService.create(contact);
        assertNotNull(created, "The created contact should not be null");
        assertEquals(contact.getEmail(), created.getEmail(), "The email should match");
        assertEquals(contact.getPhoneNumber(), created.getPhoneNumber(), "The phone number should match");
        System.out.println("Created Contact: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Contact read = contactService.read(contact.getContactId());
        assertNotNull(read, "The read contact should not be null");
        assertEquals(contact.getEmail(), read.getEmail(), "The email should match");
        assertEquals(contact.getPhoneNumber(), read.getPhoneNumber(), "The phone number should match");
        System.out.println("Read Contact: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Contact updated = contact.toBuilder()
                .email("jane.doe@example.com") // Change email for update
                .build();

        Contact result = contactService.update(updated);
        assertNotNull(result, "The updated contact should not be null");
        assertEquals(updated.getEmail(), result.getEmail(), "The updated email should match");
        System.out.println("Updated Contact: " + result);
    }

    @Test
    @Order(4)
    @Disabled("Test is disabled until delete operation is required")
    void d_delete() {
        contactService.delete(contact.getContactId());
        Contact deleted = contactService.read(contact.getContactId());
        assertNull(deleted, "The contact should be null after deletion");
        System.out.println("Deleted Contact ID: " + contact.getContactId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        Set<Contact> contacts = contactService.getAll();
        assertFalse(contacts.isEmpty(), "The set of contacts should not be empty");
        System.out.println("All Contacts: " + contacts);
    }
}
