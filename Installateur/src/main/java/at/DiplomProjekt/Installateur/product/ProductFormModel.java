package at.DiplomProjekt.Installateur.product;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Data
public class ProductFormModel {

    private Long id;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private String sku;
    private boolean isService;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductFormModel() {

    }

    public ProductFormModel(
            Long id,
            String name,
            int quantity,

            String description,
            double price,
            String sku,
            boolean isService,
            LocalDateTime createAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.quantity=quantity;
        this.description = description;
        this.price=price;
        this.sku = sku;
        this.isService = isService;
        this.createdAt = createAt;
        this.updatedAt = updatedAt;
    }



    public void setDescription(String description) {
        this.description = description;
    }
    public boolean getIsService() {
        return isService;
    }
    public void setIsService(boolean service) {
        isService = service;
    }




}
