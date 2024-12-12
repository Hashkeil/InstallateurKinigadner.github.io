package at.DiplomProjekt.Installateur.product;

import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.security.AuthenticationFacade;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import at.DiplomProjekt.Installateur.security.IAuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private IAuthenticationFacade authenticationFacade;
    private ProductRepository productRepository;

    @Autowired
    public ProductService(AuthenticationFacade authenticationFacade, ProductRepository productRepository) {
        this.authenticationFacade = authenticationFacade;
        this.productRepository = productRepository;
    }


    @Override
    public Optional<Product> findProductById(Long id) {return productRepository.findById(id);}
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override public List<Product> searchProductsStartsWith(String searchTerm) {return productRepository.findByNameContaining(searchTerm);}
    @Override public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    public Product createProductFromModel(ProductFormModel productForm) {
        // Den Benutzernamen aus dem Authentifizierungskontext abrufen und protokollieren
        String username = authenticationFacade.getAuthentication().getName();
        logger.info("getAuthentication().getName(): " + username);

        CustomUserDetails customUserDetails;
        try {
            // Versuch, den Authentifizierungsprinzipal auf CustomUserDetails zu beziehen
            customUserDetails = (CustomUserDetails) authenticationFacade.getAuthentication().getPrincipal();
                     } catch (ClassCastException e) {
                       logger.error("Failed to cast authentication principal to CustomUserDetails for user: " + username, e);
                       throw new IllegalStateException("Authentication principal is not of type CustomUserDetails", e);
                                                     }

        Product product = new Product();
                try {
                      // Produktattribute aus dem Produktformularmodell setzen
                      product.setName(productForm.getName());
                      product.setDescription(productForm.getDescription());
                      product.setSku(generateSku());
                      product.setQuantity(productForm.getQuantity());
                      product.setPrice(productForm.getPrice());
                      product.setUser(customUserDetails.getUser());
                              } catch (Exception e) {
                                // Loggen und Auslösen einer Exception, wenn ein Attribut nicht richtig gesetzt werden kann
                                logger.error("Failed to create product from model for user: " + username, e);
                                throw new IllegalArgumentException("Invalid product form data", e);
                                                     }

               try {
                     return productRepository.save(product);
                              } catch (Exception e) {
                                logger.error("Failed to save product for user: " + username, e);
                                throw new IllegalStateException("Failed to save product", e);
                                                     }
    }


    public Product createProduct(Product product) {

        CustomUserDetails userDetails = (CustomUserDetails) authenticationFacade.getAuthentication().getPrincipal();

        product.setUser(userDetails.getUser());
        product.setSku(generateSku());
        product.setQuantity(product.getQuantity());
        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setDescription(product.getDescription());
        product.setSku(product.getSku());
        return productRepository.save(product);
    }

    public Iterable<Product> findAllProducts() {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return productRepository.findAllByAccount(userDetails.getUser().getAccount());
        } else {

            logger.warn("Principal is not an instance of CustomUserDetails: " + principal);

            return Collections.emptyList();

        }
    }




    @Override
    public Product updateProduct(Long id, ProductFormModel updatedProduct) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {

            Product existingProduct = optionalProduct.get();

            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setSku(updatedProduct.getSku());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setService(updatedProduct.getIsService());

            return productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
    }


    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
    }


    private String generateSku() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHms");
        return now.format(formatter);
    }
}
