package za.ac.cput.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Order;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.factory.CustomerFactory;
import za.ac.cput.factory.OrderFactory;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService; // Assuming there's a CustomerService to handle customer operations

    private Cart savedCart;

    @BeforeEach
    void setUp() {
        // Create a Customer instance
        Customer customer = CustomerFactory.createCustomer1("test@example.com", "password123", "John Doe", "User");

        // Save the customer using the CustomerService
        Customer savedCustomer = customerService.create(customer); // Save the customer to the database

        // Create a Cart associated with the saved customer
        Cart cart = CartFactory.createCart(savedCustomer);

        // Save the cart using the CartService
        savedCart = cartService.create(cart); // Save the cart to the database
    }

    @Test
    void createOrder() {
        // Create an Order using the OrderFactory with the saved cart
        Order order = OrderFactory.buildOrder(
                savedCart,
                LocalDate.now(),
                BigDecimal.valueOf(499.99)
        );

        // Save the Order using the OrderService
        Order savedOrder = orderService.create(order);

        // Assertions to validate the creation of the order
        assertNotNull(savedOrder, "Saved order should not be null");
        assertNotNull(savedOrder.getOrderID(), "Order ID should not be null");
        assertEquals(savedCart, savedOrder.getCart(), "The cart in the order should match the saved cart");
        System.out.println("Created Order: " + savedOrder);
    }
}







