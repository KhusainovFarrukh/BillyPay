package kh.farrukh.stats_service;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.stats_service.payloads.StatsRequestDTO;
import kh.farrukh.stats_service.payloads.StatsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static kh.farrukh.stats_service.Constants.ENDPOINT_STATS;

@RestController
@RequestMapping(ENDPOINT_STATS)
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<PagingResponse<StatsResponseDTO>> getStatsList(
            @RequestParam(name = "bill_id", required = false) Long billId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(statsService.getStatsList(billId, page, pageSize));
    }

    @GetMapping("of-bill")
    public ResponseEntity<List<StatsResponseDTO>> getAllStatsOfBill(
            @RequestParam(name = "bill_id") long billId
    ) {
        return ResponseEntity.ok(statsService.getAllStatsOfBill(billId));
    }

    @GetMapping("{id}")
    public ResponseEntity<StatsResponseDTO> getStatsById(@PathVariable long id) {
        return ResponseEntity.ok(statsService.getStatsById(id));
    }

    @PostMapping
    public ResponseEntity<StatsResponseDTO> addStats(@Valid @RequestBody StatsRequestDTO statsDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statsService.addStats(statsDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<StatsResponseDTO> updateStats(
            @PathVariable long id,
            @Valid @RequestBody StatsRequestDTO statsDto
    ) {
        return ResponseEntity.ok(statsService.updateStats(id, statsDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStats(@PathVariable long id) {
        statsService.deleteStatsById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStatsByBillId(@RequestParam("bill_id") long billId) {
        statsService.deleteStatsByBillId(billId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTotalPriceOfStatsByBillId(
            @RequestParam("bill_id") long billId,
            @RequestParam("price") Double price
    ) {
        statsService.updateTotalPriceOfStatsByBillId(billId, price);
        return ResponseEntity.ok().build();
    }
}
