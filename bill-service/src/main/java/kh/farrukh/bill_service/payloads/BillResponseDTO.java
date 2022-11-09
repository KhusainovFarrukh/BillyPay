package kh.farrukh.bill_service.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.bill_service.Bill;
import kh.farrukh.bill_service.BillType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

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

    private BillType type;

    private Double price;

    // TODO: 11/8/22 response with user object
    @JsonProperty("owner_id")
    private Long ownerId;

    private List<Long> stats = new ArrayList<>();

    public BillResponseDTO(Bill bill) {
        BeanUtils.copyProperties(bill, this);
    }
}
