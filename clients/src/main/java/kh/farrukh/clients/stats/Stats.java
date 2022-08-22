package kh.farrukh.clients.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stats {

    private Long id;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    private Double amount;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("bill_id")
    private Long billId;

//    @JsonIgnoreProperties("stats")
//    @ManyToOne(optional = false)
//    private Bill bill;

//    public Stats(StatsDTO billDTO, BillRepository billRepository) {
//        BeanUtils.copyProperties(billDTO, this);
//        this.bill = billRepository.findById(billDTO.getBillId()).orElseThrow(
//                () -> new ResourceNotFoundException("Bill", "id", billDTO.getBillId())
//        );
//        this.totalPrice = this.amount * this.bill.getPrice();
//    }
}
