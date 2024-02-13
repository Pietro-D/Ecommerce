package ecommerce.backend.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotNull
    @NotBlank
    String email;

    @NotNull
    @NotBlank
    String password;
}
