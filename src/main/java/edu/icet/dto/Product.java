package edu.icet.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private Long id;
    private Integer productId;
    private String name;
    private String description;
    private String image;
    private Integer forSale;

}
