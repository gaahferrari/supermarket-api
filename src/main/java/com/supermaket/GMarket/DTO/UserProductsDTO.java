package com.supermaket.GMarket.DTO;

import com.supermaket.GMarket.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserProductsDTO extends UserDTO{
    private List<ProductDTO> favoriteProductIds;
}
