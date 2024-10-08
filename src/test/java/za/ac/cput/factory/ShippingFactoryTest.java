//package za.ac.cput.factory;
//
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.boot.test.context.SpringBootTest;
//import za.ac.cput.domain.Address;
//import za.ac.cput.domain.OrderStatus;
//import za.ac.cput.domain.Shipping;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ShippingFactoryTest {
//   private Shipping shipping;
//    @Test
//    @Order(1)
//    void createShipping_success() {
//        Address address = new Address.Builder()
//                .setStreetNumber("123")
//                .setStreetName("Main Street")
//                .setCity("Cape Town")
//                .setState("Western Cape")
//                .setPostalCode("8000")
//                .build();
//        OrderStatus orderStatus = OrderStatus.PENDING;
//        float shippingFee = 50.0f;
//
//       shipping = ShippingFactory.createShipping(address, orderStatus, BigDecimal.valueOf(shippingFee));
//
//        assertNotNull(shipping);
//        assertEquals(address, shipping.getDeliveryAddress());
//       // assertEquals(orderStatus, shipping.getOrderStatus());
//        assertEquals(shippingFee, shipping.getShippingFee());
//        System.out.println(shipping);
//    }
//
//    @Test
//    @Order(2)
//    void createShipping_invalidShippingFee() {
//        Address address = new Address.Builder()
//                .setStreetNumber("123")
//                .setStreetName("Main Street")
//                .setCity("Cape Town")
//                .setState("Western Cape")
//                .setPostalCode("8000")
//                .build();
//        OrderStatus orderStatus = OrderStatus.PENDING;
//        float shippingFee = -10.0f; // Invalid shipping fee
//
////        assertThrows(RuntimeException.class, () -> {
////            ShippingFactory.createShipping(address, orderStatus, shippingFee);
////        });
//    shipping = ShippingFactory.createShipping(address, orderStatus, BigDecimal.valueOf(shippingFee));
//        System.out.println(shipping);
//    }
//
//    @Test
//    @Order(3)
//    void createShipping_nullAddress() {
//        Address address = null; // Invalid address
//        OrderStatus orderStatus = OrderStatus.PENDING;
//        float shippingFee = 50.0f;
//
////        assertThrows(RuntimeException.class, () -> {
////            ShippingFactory.createShipping(null, orderStatus, shippingFee);
////        });
//        shipping = ShippingFactory.createShipping(address, orderStatus, BigDecimal.valueOf(shippingFee));
//        assertNotNull(shipping);
//        System.out.println(shipping);
//    }
//
//    @Test
//    @Order(4)
//    void createShipping_invalidOrderStatus() {
//        Address address = new Address.Builder()
//                .setStreetNumber("123")
//                .setStreetName("Main Street")
//                .setCity("Cape Town")
//                .setState("Western Cape")
//                .setPostalCode("8000")
//                .build();
//        OrderStatus orderStatus = null; // Invalid order status
//        float shippingFee = 50.0f;
//
////        assertThrows(RuntimeException.class, () -> {
////            ShippingFactory.createShipping(address, orderStatus, shippingFee);
////        });
//    shipping = ShippingFactory.createShipping(address, orderStatus, BigDecimal.valueOf(shippingFee));
//        System.out.println(shipping);
//    }
//}