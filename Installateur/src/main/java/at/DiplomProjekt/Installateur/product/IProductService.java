package at.DiplomProjekt.Installateur.product;

import java.util.List;
import java.util.Optional;

public interface IProductService {


    void deleteProduct(Long id);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Iterable<Product> findAllProducts();
    Product createProduct(Product product);
    Optional<Product> findProductById(Long id);
    List<Product> searchProductsStartsWith(String searchTerm);
    Product createProductFromModel(ProductFormModel productForm);
    Product updateProduct(Long id, ProductFormModel updatedProduct);
}
