package kh.farrukh.feign_clients.bill.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillResponseDTO {

    private Long id;

    private String address;

    @JsonProperty("account_number")
    private String accountNumber;

    private BillTypeDTO type;

    private Double price;

    // TODO: 11/8/22 response with user object
    @JsonProperty("owner_id")
    private Long ownerId;

    private List<Long> stats = new ArrayList<>();
}
