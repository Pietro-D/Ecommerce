package ecommerce.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseDTO {

    private Integer idAddr;

    @NotNull
    private List<Integer> idsCartElements;

    
}
