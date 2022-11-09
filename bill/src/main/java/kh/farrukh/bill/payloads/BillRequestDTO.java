package kh.farrukh.bill.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.bill.BillType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillRequestDTO {

    private String address;

    @NotBlank
    @JsonProperty("account_number")
    private String accountNumber;

    @NotNull
    private BillType type;

    @NotNull
    @Min(0)
    private Double price;

    @JsonProperty("owner_id")
    private Long ownerId;
}
