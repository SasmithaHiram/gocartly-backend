package edu.icet.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long id;
    private String name;
    private String email;
}
