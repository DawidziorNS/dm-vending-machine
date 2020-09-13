package de.dm.vendingmachine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class VendingDTO {
    @Valid
    @NotEmpty
    @NotNull
    private List<VendingItemDTO> vendingItems;
}
