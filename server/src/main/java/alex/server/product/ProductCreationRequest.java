package alex.server.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreationRequest {
    @NotBlank @NotNull private String name;
    @NotBlank @NotNull private String description;
    @NotNull private double price;
}
