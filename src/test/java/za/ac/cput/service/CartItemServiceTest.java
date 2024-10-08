package za.ac.cput.service;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.factory.CartItemFactory;
import za.ac.cput.factory.ProductFactory;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemServiceTest {
    @Autowired
    private CartItemService cartItemService;

    private static CartItem cartItem;

    @BeforeAll
    static void setUp() {
        Cart cart = CartFactory.createCart(
                new Customer(),
                Collections.emptyList()
        );


        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("Tools");

        try {
            Product product = ProductFactory.createProduct(
                    "Hammer", "A tool", BigDecimal.valueOf(199.99), "path/to/image.jpg", category);

            cartItem = CartItemFactory.createCartItem(product, cart, 2);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to create product or cart item: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void a_create() {
        CartItem created = cartItemService.create(cartItem);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    @Order(2)
    void b_read() {
        CartItem read = cartItemService.read(cartItem.getCartItemId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    @Order(3)
    void c_update() {
        CartItem updatedCartItem = cartItem.toBuilder()
                .quantity(3) // Example updated quantity
                .build();
        updatedCartItem.calculateItemTotalPrice(); // Recalculate total price after updating quantity
        CartItem updated = cartItemService.update(updatedCartItem);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Disabled
    @Order(4)
    void d_delete() {
        cartItemService.delete(cartItem.getCartItemId());
        System.out.println("CartItem deleted where CartItem ID: " + cartItem.getCartItemId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println(cartItemService.getAll());
    }
}



