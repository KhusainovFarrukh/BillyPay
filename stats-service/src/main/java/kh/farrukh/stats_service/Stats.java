package kh.farrukh.stats_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.stats_service.payloads.StatsRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;

import static kh.farrukh.stats_service.Constants.TABLE_NAME_STATS;

@Entity
@Table(name = TABLE_NAME_STATS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("bill_id")
    @Column(nullable = false)
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

    public Stats(StatsRequestDTO billDTO, Double price) {
        BeanUtils.copyProperties(billDTO, this);
        this.totalPrice = this.amount * price;
    }
}
