package kh.farrukh.bill_service;

import kh.farrukh.bill_service.payloads.BillRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

import static kh.farrukh.bill_service.Constants.*;

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

    @Column(nullable = false)
    private Long ownerId;

    @ElementCollection
    private List<Long> stats = new ArrayList<>();

//    @JsonIgnoreProperties("bill")
//    @OneToMany(mappedBy = "bill", orphanRemoval = true)
//    private List<Stats> stats = new ArrayList<>();

//    public Bill(BillDTO billDTO, UserRepository userRepository) {
//        BeanUtils.copyProperties(billDTO, this);
//        this.owner = userRepository.findById(billDTO.getOwnerId()).orElseThrow(
//            () -> new ResourceNotFoundException("User", "id", billDTO.getOwnerId())
//        );
//    }

    public Bill(BillRequestDTO billDTO) {
        BeanUtils.copyProperties(billDTO, this);
    }
}
