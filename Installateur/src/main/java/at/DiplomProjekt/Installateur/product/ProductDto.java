package at.DiplomProjekt.Installateur.product;

import jdk.jfr.DataAmount;
import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String sku;
    private String name;
    private boolean service;
    private String description;
    private double price;

    public ProductDto(Long id, String name, String description,double price, String sku, boolean service) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price=price;
        this.sku = sku;
        this.service = service;
    }


}