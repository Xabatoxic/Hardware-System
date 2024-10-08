//package za.ac.cput.factory;
//
//import org.junit.jupiter.api.*;
//import org.springframework.boot.test.context.SpringBootTest;
//import za.ac.cput.domain.*;
//import za.ac.cput.domain.Order;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class PaymentFactoryTest {
//
//    private Order createValidOrder() {
//        Contact contact = ContactFactory.createContact("john.doe@example.com", "0783139988");
//        Customer customer = CustomerFactory.createCustomer1(contact.getEmail(), "soon","123456", "Customer");
//        Cart cart = CartFactory.createCart(customer);
//        Address address = AddressFactory.createAddress("21", "", "", "", "", "Main Street", "Capetown", "Western Cape", "8008");
//        Shipping shipping = ShippingFactory.createShipping(address, OrderStatus.PENDING, BigDecimal.valueOf(50.0f));
//        return OrderFactory.buildOrder(cart, LocalDate.now(), shipping, BigDecimal.valueOf(200.0f), "PENDING");
//    }
//
//    @Test
//   // @Order(1)
//     void createPayment_success() {
//        Order order = createValidOrder();
//        LocalDate paymentDate = LocalDate.now();
//        BigDecimal totalPrice = order.getTotalAmount();
//        PaymentMethod method = PaymentMethod.CREDIT_CARD;
//
//        Payment payment = PaymentFactory.createPayment(order, paymentDate, totalPrice, method);
//
//        assertNotNull(payment);
//        assertEquals(order, payment.getOrder());
//        assertEquals(paymentDate, payment.getPaymentDate());
//        assertEquals(totalPrice, payment.getTotalPrice());
//        assertEquals(method, payment.getMethod());
//        System.out.println(payment);
//    }
//
//    @Test
//  //  @Order(2)
//    void createPayment_invalidPaymentDate() {
//        Order order = createValidOrder();
//        LocalDate paymentDate = LocalDate.now().plusDays(1);
//        BigDecimal totalPrice = order.getTotalAmount();
//        PaymentMethod method = PaymentMethod.CREDIT_CARD;
//
//        assertThrows(RuntimeException.class, () -> {
//            PaymentFactory.createPayment(order, paymentDate, totalPrice, method);
//        });
//    }
//
//    @Test
//  //  @Order(3)
//    void createPayment_nullOrder() {
//        LocalDate paymentDate = LocalDate.now();
//        float totalPrice = 100.0f;
//        PaymentMethod method = PaymentMethod.CREDIT_CARD;
//
//        assertThrows(RuntimeException.class, () -> {
//            PaymentFactory.createPayment(null, paymentDate, BigDecimal.valueOf(totalPrice), method);
//        });
//    }
//
//    @Test
//   // @Order(4)
//    void createPayment_invalidTotalPrice() {
//        Order order = createValidOrder();
//        LocalDate paymentDate = LocalDate.now();
//        float totalPrice = -100.0f;
//        PaymentMethod method = PaymentMethod.CREDIT_CARD;
//
//        assertThrows(RuntimeException.class, () -> {
//            PaymentFactory.createPayment(order, paymentDate, BigDecimal.valueOf(totalPrice), method);
//        });
//    }
//
//    @Test
//    //@Order(5)
//    void createPayment_invalidPaymentMethod() {
//        Order order = createValidOrder();
//        LocalDate paymentDate = LocalDate.now();
//        BigDecimal totalPrice = order.getTotalAmount();
//        PaymentMethod method = null;
//
//        assertThrows(RuntimeException.class, () -> {
//            PaymentFactory.createPayment(order, paymentDate, totalPrice, method);
//        });
//    }
//}