package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Order;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.factory.OrderFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api";
    private static Order savedOrder;

    private Cart createValidCart() {
        // Implement your Cart creation logic here or use a factory
        return CartFactory.createCart(null); // Adjust as necessary
    }

    @BeforeAll
    static void setUp() {
        // Implement any setup logic if necessary
    }

    @Test
    void a_createOrder() {
        Cart cart = createValidCart();
        LocalDate orderDate = LocalDate.now();
        BigDecimal totalAmount = BigDecimal.valueOf(500.00).setScale(2, BigDecimal.ROUND_HALF_UP);

        Order order = OrderFactory.buildOrder(cart, orderDate, totalAmount);
        ResponseEntity<Order> response = restTemplate.postForEntity(BASE_URL + "/create", order, Order.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        savedOrder = response.getBody();
        assertEquals(totalAmount, savedOrder.getTotalAmount());
        assertEquals(orderDate, savedOrder.getOrderDate());
        assertEquals(cart.getCartId(), savedOrder.getCart().getCartId()); // Assuming Cart has a getCartId method
        System.out.println("Saved Order: " + savedOrder);
    }

//    @Test
//    void b_readOrder() {
//        String url = BASE_URL + "/read/" + savedOrder.getOrderID();
//        ResponseEntity<Order> response = restTemplate.getForEntity(url, Order.class);
//
//        assertNotNull(response.getBody());
//        assertEquals(savedOrder.getOrderID(), response.getBody().getOrderID());
//        System.out.println("Read Order: " + response.getBody());
//    }

    @Test
    void c_updateOrder() {
        Order updatedOrder = savedOrder.toBuilder()
                .totalAmount(BigDecimal.valueOf(600.00).setScale(2, BigDecimal.ROUND_HALF_UP))
                .build();
        ResponseEntity<Order> response = restTemplate.postForEntity(BASE_URL + "/update", updatedOrder, Order.class);

        assertNotNull(response.getBody());
        assertEquals(BigDecimal.valueOf(600.00).setScale(2, BigDecimal.ROUND_HALF_UP), response.getBody().getTotalAmount());
        System.out.println("Updated Order: " + response.getBody());
    }

//    @Test
//    void d_deleteOrder() {
//        String url = BASE_URL + "/delete/" + savedOrder.getOrderID();
//        restTemplate.delete(url);
//
//        ResponseEntity<Order> response = restTemplate.getForEntity(BASE_URL + "/read/" + savedOrder.getOrderID(), Order.class);
//        assertNull(response.getBody());
//        System.out.println("Deleted Order ID: " + savedOrder.getOrderID());
//    }

    @Test
    void e_getAllOrders() {
        ResponseEntity<Set> response = restTemplate.exchange(BASE_URL + "/getAll", HttpMethod.GET, null, Set.class);

        assertNotNull(response.getBody());
        System.out.println("All Orders: " + response.getBody());
    }
}










