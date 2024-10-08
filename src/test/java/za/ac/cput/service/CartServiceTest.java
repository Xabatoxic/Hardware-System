package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Customer;
import za.ac.cput.factory.CartFactory;
import java.math.BigDecimal;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceTest {
    @Autowired
    private CartService cartService;

    private static Cart cart;

    @BeforeAll
    static void setUp() {
        cart = CartFactory.createCart(
                new Customer(),
                Collections.emptyList()
        );
    }

    @Test
    @Order(1)
    void a_create() {
        Cart created = cartService.create(cart);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    @Order(2)
    void b_read() {
        Cart read = cartService.read(cart.getCartId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    @Order(3)
    void c_update() {
        Cart updatedCart = cart.toBuilder()
                .totalPrice(BigDecimal.valueOf(599.99)) // Example updated total price
                .build();
        Cart updated = cartService.update(updatedCart);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Disabled
    @Order(4)
    void d_delete() {
        cartService.delete(cart.getCartId());
        System.out.println("Cart deleted where Cart ID: " + cart.getCartId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println(cartService.getAll());
    }
}

