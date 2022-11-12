package kh.farrukh.feign_clients.stats.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsResponseDTO {

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
}
