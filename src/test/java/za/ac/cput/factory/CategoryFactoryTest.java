package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;

import javax.sql.rowset.serial.SerialBlob;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryFactoryTest {

    private Product product1;
    private Product product2;
    private final String categoryName = "Power Tools";
    private final String categoryDescription = "Tools powered by electricity or battery";
    private final String imagePath = "C:\\Users\\Rupert Van Niekerk\\Documents\\ShareX\\Screenshots\\2024-04\\msedge_F7HspUtQqf.png";

    @BeforeEach
    void setUp() throws Exception {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        Blob imageBlob = new SerialBlob(imageBytes);

        product1 = Product.builder()
                .name("Power Drill")
                .description("High-speed power drill for home and professional use")
                .price(BigDecimal.valueOf((699.99f)))
                .image(imageBlob)
                .build();

        product2 = Product.builder()
                .name("Electric Saw")
                .description("Efficient electric saw for cutting wood and metal")
                .price(BigDecimal.valueOf((499.99f)))
                .image(imageBlob)
                .build();
    }

    @Test
    @Order(1)
    void createCategoryWithJustName() throws Exception {
        Category category = CategoryFactory.createCategory(categoryName, null, null, null);

        assertNotNull(category);
        //assertEquals(categoryName, category.getName());
        //assertNull(category.getImage());
        //assertTrue(category.getProducts().isEmpty());

        System.out.println("Category created with just name: " + category);
    }

    @Test
    @Order(2)
    void createCategoryWithProducts() throws Exception {
        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);

        Category category = CategoryFactory.createCategory(categoryName, categoryDescription, imagePath, products);

        assertNotNull(category);
        assertEquals(categoryName, category.getName());
        assertEquals(categoryDescription, category.getDescription());
        assertNotNull(category.getImage());
        assertFalse(category.getProducts().isEmpty());
        assertEquals(2, category.getProducts().size());
        assertTrue(category.getProducts().contains(product1));
        assertTrue(category.getProducts().contains(product2));

        System.out.println("Category with products: " + category);
    }

    @Test
    @Order(3)
    void createCategoryWithInvalidName() throws Exception {

        Category category = CategoryFactory.createCategory(null, categoryDescription, imagePath, new HashSet<>());

        assertNotNull(category.getName());




    }
}
