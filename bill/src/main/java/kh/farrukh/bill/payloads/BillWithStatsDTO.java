package kh.farrukh.bill.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.bill.Bill;
import kh.farrukh.bill.BillType;
import kh.farrukh.clients.stats.Stats;
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
public class BillWithStatsDTO {

    private Long id;

    private String address;

    @JsonProperty("account_number")
    private String accountNumber;

    private BillType type;

    private Double price;

//    @JsonIgnoreProperties("bills")
//    @ManyToOne(optional = false)
//    private AppUser owner;

    private List<Stats> stats = new ArrayList<>();

//    @JsonIgnoreProperties("bill")
//    @OneToMany(mappedBy = "bill", orphanRemoval = true)
//    private List<Stats> stats = new ArrayList<>();


    public BillWithStatsDTO(Bill bill, List<Stats> stats) {
        BeanUtils.copyProperties(bill, this);
        this.stats = stats;
    }
}
