package com.jordinymontanez.store.shopping.model;

import lombok.Builder;
import lombok.Data;

@Data //Genera Getter y Setter
@Builder
public class Category {
    private  Long id;
    private String name;
}
