package kh.farrukh.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static kh.farrukh.stats.Constants.TABLE_NAME_STATS;

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

    @NotNull
    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @NotNull
    private Double amount;

    @NotNull
    @JsonProperty("total_price")
    private Double totalPrice;

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

    public Stats(StatsDTO billDTO) {
        BeanUtils.copyProperties(billDTO, this);
        this.totalPrice = this.amount * 1;
    }
}
