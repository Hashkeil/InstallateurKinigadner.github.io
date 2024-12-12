package at.DiplomProjekt.Installateur.product;


import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.security.AuthenticationFacade;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import at.DiplomProjekt.Installateur.security.IAuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private IAuthenticationFacade authenticationFacade;
    private IProductService productService;

    private ProductService productService1;
    public ProductController(AuthenticationFacade authenticationFacade, ProductService productService) {
        this.authenticationFacade = authenticationFacade;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product_list";
    }



    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new ProductFormModel());
        return "product_create";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute("product") ProductFormModel formModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            model.addAttribute("product", formModel);
            return "product_create";
        }
        productService.createProductFromModel(formModel);
        return "redirect:/products/list";
    }

    @GetMapping("/autocomplete/{term}")
    public @ResponseBody List<ProductDto> searchProductByNameStartsWith(@PathVariable String term) {
        List<ProductDto> productList = new ArrayList<>();

        productService.searchProductsStartsWith(term).forEach(product -> {
            productList.add(new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getSku(),
                    product.isService()
            ));
        });
        return productList;
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long productId, Model model) {
        // Retrieve the product by ID
        Product product = productService.getProductById(productId);

        // Check if the product exists
        if (product == null) {return "redirect:/products/list?error=ProductNotFound";}

        // Pass the product directly to the view
        model.addAttribute("product", product);

        // Return the view name for the edit form
        return "product_edit";
    }


    @PostMapping("/update/{id}")
    public String updateProduct(
            @PathVariable("id") long productId,
            @Valid @ModelAttribute("product") ProductFormModel formModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, redirect back to the edit form with error messages
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            redirectAttributes.addFlashAttribute("product", formModel);
            return "redirect:/products/edit/" + productId;
        }
        try {
            // Call the ProductService to update the product
            productService.updateProduct(productId, formModel);
            // Redirect to the product list page after successful update
            return "redirect:/products/list";
        } catch (ProductNotFoundException e) {
            // Handle product not found exception gracefully
            return "redirect:/products/list?error=ProductNotFound";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long productId) {
        try {
            productService.deleteProduct(productId);
            return "redirect:/products/list";
        } catch (ProductNotFoundException e) {
            return "redirect:/products/list?error=ProductNotFound";
        }
    }





}
