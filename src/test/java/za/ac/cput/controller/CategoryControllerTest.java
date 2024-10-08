package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.ProductFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/categories";
    private static Category category;
    private static Category savedCategory;

    @BeforeAll
    static void setUp() {
        try {

            category = CategoryFactory.createCategoryWithoutProducts(
                    "Construction Tools",
                    "Tools for heavy-duty construction work",
                    "path/to/category/image"
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test category", e);
        }
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Category> categoryResponseEntity =
                restTemplate.postForEntity(url, category, Category.class);
        assertNotNull(categoryResponseEntity);
        assertNotNull(categoryResponseEntity.getBody());

        savedCategory = categoryResponseEntity.getBody();
        System.out.println("Saved Category: " + savedCategory);

        // Now that the Category is saved, create the products and associate them with the Category
        try {
            Product product = ProductFactory.createProduct(
                    "Hammer",
                    "Heavy-duty hammer for construction",
                    BigDecimal.valueOf(29.99),
                    "path/to/hammer/image",
                    savedCategory
            );
            Set<Product> products = new HashSet<>(Collections.singleton(product));
            savedCategory.setProducts(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test product", e);
        }
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + savedCategory.getCategoryId();
        ResponseEntity<Category> response = restTemplate.getForEntity(url, Category.class);
        assertNotNull(response.getBody());
        System.out.println("Read Category: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Category updatedCategory = savedCategory.toBuilder().name("Updated Construction Tools").build();
        String url = BASE_URL + "/update";
        ResponseEntity<Category> response = restTemplate.postForEntity(url, updatedCategory, Category.class);
        assertNotNull(response.getBody());
        assertEquals("Updated Construction Tools", response.getBody().getName());
        System.out.println("Updated Category: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_delete() {
        String url = BASE_URL + "/delete/" + savedCategory.getCategoryId();
        restTemplate.delete(url);

        // Verify deletion
        ResponseEntity<Category> response = restTemplate.getForEntity(BASE_URL + "/read/" + savedCategory.getCategoryId(), Category.class);
        assertNull(response.getBody());
        System.out.println("Deleted Category ID: " + savedCategory.getCategoryId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        String url = BASE_URL + "/all";
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
        System.out.println("All Categories: ");
        System.out.println(response.getBody());
    }
}
