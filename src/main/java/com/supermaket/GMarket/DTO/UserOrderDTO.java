package com.supermaket.GMarket.DTO;

import com.supermaket.GMarket.entity.User;
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
public class UserOrderDTO extends UserDTO {
    private List<OrderDTO> orderIds;
}
