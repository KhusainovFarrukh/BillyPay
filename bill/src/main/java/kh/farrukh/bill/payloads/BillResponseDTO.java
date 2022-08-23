package kh.farrukh.bill.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.bill.Bill;
import kh.farrukh.bill.BillType;
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

//    @JsonIgnoreProperties("bills")
//    @ManyToOne(optional = false)
//    private AppUser owner;

    private List<Long> stats = new ArrayList<>();

    public BillResponseDTO(Bill bill) {
        BeanUtils.copyProperties(bill, this);
    }
}
