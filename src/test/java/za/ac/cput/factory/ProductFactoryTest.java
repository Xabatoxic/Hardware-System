package za.ac.cput.factory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductFactoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ProductFactoryTest.class);

    @Test
    void createProduct() throws Exception {
        // Create test data
        Category category = CategoryFactory.createCategoryWithoutProducts("Tools",
                "Various hardware tools",
                "C:\\Users\\Rupert Van Niekerk\\Documents\\ShareX\\Screenshots\\2024-04\\msedge_F7HspUtQqf.png");

        Product productWithImage = ProductFactory.createProduct("Power Drill",
                        "High-speed power drill for home and professional use",
                BigDecimal.valueOf((699.99f)),
                "",
 category);

        assertNotNull(productWithImage);

        System.out.println(productWithImage);
       // logger.info("Product created successfully: {}", productWithImage);
    }


}