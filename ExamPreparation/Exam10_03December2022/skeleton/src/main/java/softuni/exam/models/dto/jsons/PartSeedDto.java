package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;
import java.io.Serializable;

public class PartSeedDto implements Serializable {
    @Expose
    @Size(min = 2, max = 19)
    @NotNull
    private String partName;
    @Expose
    @DecimalMin(value = "10.0")
    @DecimalMax(value = "2000.0")
    @NotNull
    private Double price;
    @Expose
    @Positive
    @NotNull
    private Integer quantity;

    public PartSeedDto() {
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
