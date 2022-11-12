package kh.farrukh.feign_clients.bill.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.feign_clients.stats.payloads.StatsResponseDTO;
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
public class BillWithStatsResponseDTO {

    private Long id;

    private String address;

    @JsonProperty("account_number")
    private String accountNumber;

    private BillTypeDTO type;

    private Double price;

    // TODO: 11/8/22 response with user object
    @JsonProperty("owner_id")
    private Long ownerId;

    private List<StatsResponseDTO> stats = new ArrayList<>();

//    @JsonIgnoreProperties("bill")
//    @OneToMany(mappedBy = "bill", orphanRemoval = true)
//    private List<Stats> stats = new ArrayList<>();
}
