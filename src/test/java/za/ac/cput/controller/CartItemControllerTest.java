package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.factory.CartItemFactory;
import za.ac.cput.factory.CustomerFactory;
import za.ac.cput.factory.ProductFactory;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/cart-items";

    private static Customer customer;
    private static Cart cart;
    private static Product product;
    private static CartItem cartItem;
    private static CartItem savedCartItem;

    @BeforeAll
    static void setup() throws Exception {
        customer = CustomerFactory.createCustomer2(
                "luckiesmpumlwana@gmail.com", "soon","Lucky", "Mpumlwana",
                null, "lucky123", "Customer", Collections.emptyList());

        cart = CartFactory.createCart(customer);
        product = ProductFactory.createProduct(
                "Hammer", "A tool", BigDecimal.valueOf(199.99), "path/to/image.jpg", null);
        cartItem = CartItemFactory.createCartItem(product, cart, 2);
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<CartItem> response = restTemplate.postForEntity(url, cartItem, CartItem.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        savedCartItem = response.getBody();
        assertNotNull(savedCartItem);
        assertEquals(cartItem.getProduct(), savedCartItem.getProduct());
        assertEquals(cartItem.getCart(), savedCartItem.getCart());
        assertEquals(cartItem.getQuantity(), savedCartItem.getQuantity());
        assertEquals(cartItem.getItemTotalPrice(), savedCartItem.getItemTotalPrice());
        System.out.println("Saved CartItem: " + savedCartItem);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = get_URL("/read/" + savedCartItem.getCartItemId());
        ResponseEntity<CartItem> response = restTemplate.getForEntity(url, CartItem.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("READ: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        CartItem updatedCartItem = savedCartItem.toBuilder()
                .quantity(3)
                .build();
        updatedCartItem.calculateItemTotalPrice(); // Recalculate total price after updating quantity
        String url = BASE_URL + "/update";
        ResponseEntity<CartItem> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedCartItem), CartItem.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getQuantity());
        assertEquals(updatedCartItem.getItemTotalPrice(), response.getBody().getItemTotalPrice());
        System.out.println("Updated CartItem: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_delete() {
        String url = get_URL("/delete/" + savedCartItem.getCartItemId());
        restTemplate.delete(url);

        // Check if the cart item has been deleted
        ResponseEntity<CartItem> response = restTemplate.getForEntity(get_URL("/read/" + savedCartItem.getCartItemId()), CartItem.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("CartItem " + savedCartItem.getCartItemId() + " successfully deleted!!!");
    }

    @Test
    @Order(5)
    void e_getAll() {
        String url = get_URL("/all");
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("All CartItems : ");
        System.out.println(response.getBody());
    }

    private String get_URL(String url) {
        return BASE_URL + url;
    }
}

