package com.supermaket.GMarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CategoryProductDTO extends CategoryDTO{

    private Set<ProductDTO> productsIds;
}
