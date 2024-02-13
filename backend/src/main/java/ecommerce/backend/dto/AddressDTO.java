package ecommerce.backend.dto;



import ecommerce.backend.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private Integer id;

    @NotNull
    @NotBlank
    private String addressLine;

    @NotNull
    @NotBlank
    private String postalCode;
    
    @NotNull
    @NotBlank
    private String country;

    @NotNull
    @NotBlank
    private String city;

    public void copyInfo(Address addr){
        this.id=addr.getId();
        this.addressLine=addr.getAddressLine();
        this.postalCode=addr.getPostalCode();
        this.city=addr.getCity();
        this.country=addr.getCountry();
    }
    
    
    
}
