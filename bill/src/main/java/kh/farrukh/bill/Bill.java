package kh.farrukh.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static kh.farrukh.bill.Constants.*;

@Entity
@Table(name = TABLE_NAME_BILL,
        uniqueConstraints = @UniqueConstraint(name = "uk_bill_account_number", columnNames = "account_number"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME_BILL_ID)
    @SequenceGenerator(name = GENERATOR_NAME_BILL_ID, sequenceName = SEQUENCE_NAME_BILL_ID)
    private Long id;

    private String address;

    @NotBlank
    @JsonProperty("account_number")
    @Column(nullable = false, name = "account_number")
    private String accountNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BillType type;

    @Column(nullable = false)
    private Double price;

//    @JsonIgnoreProperties("bills")
//    @ManyToOne(optional = false)
//    private AppUser owner;

//    @JsonIgnoreProperties("bill")
//    @OneToMany(mappedBy = "bill", orphanRemoval = true)
//    private List<Stats> stats = new ArrayList<>();

//    public Bill(BillDTO billDTO, UserRepository userRepository) {
//        BeanUtils.copyProperties(billDTO, this);
//        this.owner = userRepository.findById(billDTO.getOwnerId()).orElseThrow(
//            () -> new ResourceNotFoundException("User", "id", billDTO.getOwnerId())
//        );
//    }

    public Bill(BillDTO billDTO) {
        BeanUtils.copyProperties(billDTO, this);
    }
}
