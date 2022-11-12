package kh.farrukh.stats_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private Long billId;

//    @JsonIgnoreProperties("stats")
//    @ManyToOne(optional = false)
//    private Bill bill;
}
