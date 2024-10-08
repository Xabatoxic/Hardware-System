//package za.ac.cput.service;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import za.ac.cput.domain.Shipping;
//import za.ac.cput.domain.Address;
//import za.ac.cput.domain.OrderStatus;
//import za.ac.cput.factory.ShippingFactory;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ShippingServiceTest {
//
//    @Autowired
//    private ShippingService shippingService;
//
//    private static Shipping shipping;
//    private static Address address;
//
//    @BeforeAll
//    static void setUp() {
//        address = new Address();
//        OrderStatus orderStatus = OrderStatus.IN_TRANSIT;
//        shipping = ShippingFactory.createShipping(address, orderStatus, BigDecimal.valueOf(49.99));
//    }
//
//    @Test
//    @Order(1)
//    void a_create() {
//        Shipping created = shippingService.create(shipping);
//        assertNotNull(created);
//        System.out.println(created);
//    }
//
//    @Test
//    @Order(2)
//    void b_read() {
//        Shipping read = shippingService.read(shipping.getShippingID());
//        assertNotNull(read);
//        System.out.println(read);
//    }
//
//    @Test
//    @Order(3)
//    void c_update() {
//        Shipping updatedShipping = shipping.toBuilder().orderStatus(OrderStatus.DELIVERED).build();
//        Shipping updated = shippingService.update(updatedShipping);
//        assertNotNull(updated);
//        System.out.println(updated);
//    }
//
//    @Test
//    @Disabled
//    @Order(4)
//    void d_delete() {
//        shippingService.delete(shipping.getShippingID());
//        System.out.println("Shipping deleted where Shipping ID: " + shipping.getShippingID());
//    }
//
//    @Test
//    @Order(5)
//    void e_getAll() {
//        System.out.println(shippingService.getAll());
//    }
//}

