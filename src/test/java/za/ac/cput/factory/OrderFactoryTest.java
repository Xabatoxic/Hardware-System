package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.*;
import za.ac.cput.domain.Order;
import za.ac.cput.factory.OrderFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderFactoryTest {

    private Cart createValidCart() {
        Contact contact = ContactFactory.createContact("luckiesmpumlwana@gmail.com", "0783139988");
        Customer customer = CustomerFactory.createCustomer1(contact.getEmail(), "soon", "123456", "Customer");
        return CartFactory.createCart(customer);
    }

    @Test
    @org.springframework.core.annotation.Order(1)
    void testBuildOrderWithAllFields() {
        Cart cart = createValidCart();
        LocalDate orderDate = LocalDate.now();
        BigDecimal totalAmount = BigDecimal.valueOf(500.0f).setScale(2, BigDecimal.ROUND_HALF_UP);

        Order order = OrderFactory.buildOrder(cart, orderDate, totalAmount);
        assertNotNull(order);
        assertEquals(totalAmount, order.getTotalAmount());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(cart, order.getCart());
        System.out.println(order);
    }

    @Test
    @org.springframework.core.annotation.Order(2)
    void testBuildOrderWithEmptyOrderItems() {
        Cart cart = createValidCart();
        LocalDate orderDate = LocalDate.now();
        BigDecimal totalAmount = BigDecimal.valueOf(0.0f).setScale(2, BigDecimal.ROUND_HALF_UP);

        Order order = OrderFactory.buildOrder(cart, orderDate, totalAmount);
        assertNotNull(order);
        assertEquals(totalAmount, order.getTotalAmount());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(cart, order.getCart());
        System.out.println(order);
    }
}


