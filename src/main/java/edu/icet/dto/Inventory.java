package edu.icet.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Inventory {
    private Long id;
    private Long itemId;
    private Long productId;
    private Integer quantity;

}
