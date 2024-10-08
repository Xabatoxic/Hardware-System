package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Customer;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.factory.CustomerFactory;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/ecommerce/cart";

    private static Customer customer = CustomerFactory.createCustomer2(
            "luckiesmpumlwana@gmail.com","soon", "Lucky", "Mpumlwana",
            null, "lucky123", "Customer", Collections.emptyList());

    private static Cart cart = CartFactory.createCart(customer);
    private static Cart savedCart;

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Cart> cartResponseEntity =
                restTemplate.postForEntity(url, cart, Cart.class);
        assertNotNull(cartResponseEntity);
        assertNotNull(cartResponseEntity.getBody());

        savedCart = cartResponseEntity.getBody();
        cart = Cart.builder()
                .cartId(savedCart.getCartId())
                .customer(savedCart.getCustomer())
                .itemsQuantity(savedCart.getItemsQuantity())
                .cartItems(savedCart.getCartItems())
                .totalPrice(savedCart.getTotalPrice())
                .build();
        System.out.println("Saved Cart: " + cart);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = get_URL("/read/" + savedCart.getCartId());
        System.out.println("URL : " + url);
        ResponseEntity<Cart> response = restTemplate.getForEntity(url, Cart.class);
        assertNotNull(response.getBody());
        System.out.println("READ: " + savedCart);
    }

    @Test
    @Order(3)
    void c_update() {
        Cart updatedCart = savedCart.toBuilder()
                .itemsQuantity(5)
                .totalPrice(BigDecimal.valueOf(150.00))
                .build();
        String url = BASE_URL + "/update";
        ResponseEntity<Cart> response = restTemplate.postForEntity(url, updatedCart, Cart.class);
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getItemsQuantity());
        System.out.println("Updated Cart: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_delete() {
        System.out.println("Cart ID to delete: " + savedCart.getCartId());
        String url = get_URL("/delete/" + savedCart.getCartId());
        System.out.println("URL for delete: " + url);
        restTemplate.delete(url);

        // Check if the cart has been deleted
        ResponseEntity<Cart> response = restTemplate.getForEntity(get_URL("/read/" + savedCart.getCartId()), Cart.class);
        assertNull(response.getBody());
        System.out.println("Cart " + savedCart.getCartId() + " successfully deleted!!!");
    }

    @Test
    @Order(5)
    void e_getAll() {
        String url = get_URL("/all");
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("All Carts : ");
        System.out.println(response);
        System.out.println(response.getBody());
    }

    public String get_URL(String url) {
        return BASE_URL + url;
    }
}

