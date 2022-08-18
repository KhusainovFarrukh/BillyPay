package kh.farrukh.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    private Long id;

    private String address;

    @JsonProperty("account_number")
    private String accountNumber;

    private BillType type;

    private Double price;

//    @JsonIgnoreProperties("bills")
//    @ManyToOne(optional = false)
//    private AppUser owner;

//    @JsonIgnoreProperties("bill")
//    @OneToMany(mappedBy = "bill", orphanRemoval = true)
//    private List<Stats> stats = new ArrayList<>();
}
