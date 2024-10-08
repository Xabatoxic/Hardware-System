package za.ac.cput.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Contact;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class AddressFactoryTest {

    @Test
    @Order(1)
    void a_createStandaloneAddress_success() {
        Address address = AddressFactory.createAddress("21", "", "", "", "", "Main Street", "Capetown", "Western Cape", "8008");
        assertNotNull(address);
        System.out.println(address.toString());
    }

    @Test
    @Order(2)
    void b_createApartmentAddress_success() {
        Address address = AddressFactory.createAddress("2", "", "", "Sunset Complex", "A12", "Main Street", "Capetown", "Western Cape", "8008");
        assertNotNull(address);
        System.out.println(address.toString());
    }

    @Test
    @Order(3)
    void c_createUnitAddress_success() {
        Address address = AddressFactory.createAddress("5", "3B", "", "Highrise Building", "", "Main Street", "Capetown", "Western Cape", "8008");
        assertNotNull(address);
        System.out.println(address.toString());
    }

    @Test
    @Order(4)
    void d_createAddress_failed_missingStreetNumber() {
        Address address = AddressFactory.createAddress("", "", "", "", "", "Main Street", "Capetown", "Western Cape", "8008");
        assertNotNull(address);
        System.out.println(address.toString());
    }

    @Test
    @Order(5)
    void e_createAddress_failed_invalidPostalCode() {
        Address address = AddressFactory.createAddress("21", "", "", "", "", "Main Street", "Capetown", "Western Cape", "ABCD");
        assertNotNull(address);
        System.out.println(address.toString());
    }

    @Test
    @Order(6)
    void f_createAddress_failed_missingEssentialFields() {
        Address address = AddressFactory.createAddress("", "", "", "", "", "", "Capetown", "Western Cape", "8008");
        assertNotNull(address);
        System.out.println(address.toString());
    }

    @Test
    @Order(7)
    void g_createAddress_failed_invalidApartmentCombination() {
        Address address = AddressFactory.createAddress("", "", "", "Sunset Complex", "", "Main Street", "Capetown", "Western Cape", "8008");
        assertNotNull(address);
        System.out.println(address.toString());
    }

    @Test
    @Order(8)
    void h_createAddress_failed_invalidUnitCombination() {
        Address address = AddressFactory.createAddress("", "3B", "", "", "", "Main Street", "Capetown", "Western Cape", "8008");
        assertNotNull(address);
        System.out.println(address.toString());
    }

}