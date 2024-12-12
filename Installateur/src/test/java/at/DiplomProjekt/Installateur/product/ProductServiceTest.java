package at.DiplomProjekt.Installateur.product;

import at.DiplomProjekt.Installateur.security.AuthenticationFacade;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import at.DiplomProjekt.Installateur.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findProductById_ExistingId_ShouldReturnProduct() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> resultProduct = productService.findProductById(id);

        assertTrue(resultProduct.isPresent());
        assertEquals(product, resultProduct.get());
    }

    @Test
    void searchProductsStartsWith_ExistingTerm_ShouldReturnProducts() {
        String searchTerm = "Test";
        List<Product> expectedProducts = Collections.singletonList(new Product());
        when(productRepository.findByNameContaining(searchTerm)).thenReturn(expectedProducts);

        List<Product> resultProducts = productService.searchProductsStartsWith(searchTerm);

        assertFalse(resultProducts.isEmpty());
        assertEquals(expectedProducts.size(), resultProducts.size());
        assertEquals(expectedProducts.get(0), resultProducts.get(0));
    }

    @Test
    void createProductFromModel_ShouldCreateAndReturnProduct() {
        // Setup test data and mocks
        ProductFormModel productForm = new ProductFormModel();
        productForm.setName("Test Product");
        productForm.setDescription("Description");

        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        User user = new User();

        // Mock Authentication object and its behavior
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);

        // Ensure AuthenticationFacade mock returns the mocked Authentication object
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);

        // Continue with user and product setup
        when(customUserDetails.getUser()).thenReturn(user);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Exercise the method under test
        Product createdProduct = productService.createProductFromModel(productForm);

        // Assertions to verify the behavior
        assertNotNull(createdProduct);
        assertEquals(productForm.getName(), createdProduct.getName());
        assertEquals(productForm.getDescription(), createdProduct.getDescription());
        assertEquals(user, createdProduct.getUser());
    }


}
