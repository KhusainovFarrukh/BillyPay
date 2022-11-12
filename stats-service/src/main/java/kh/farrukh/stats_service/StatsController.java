package kh.farrukh.stats_service;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.stats.payloads.StatsRequestDTO;
import kh.farrukh.feign_clients.stats.payloads.StatsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static kh.farrukh.feign_clients.stats.StatsConstants.*;

@RestController
@RequestMapping(ENDPOINT_STATS)
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<PagingResponse<StatsResponseDTO>> getStatsList(
            @RequestParam(name = PARAM_BILL_ID, required = false) Long billId,
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(statsService.getStatsList(billId, page, pageSize));
    }

    @GetMapping(ENDPOINT_POSTFIX_OF_BILL)
    public ResponseEntity<List<StatsResponseDTO>> getAllStatsOfBill(
            @RequestParam(name = PARAM_BILL_ID) long billId
    ) {
        return ResponseEntity.ok(statsService.getAllStatsOfBill(billId));
    }

    @GetMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<StatsResponseDTO> getStatsById(@PathVariable(PARAM_ID) long id) {
        return ResponseEntity.ok(statsService.getStatsById(id));
    }

    @PostMapping
    public ResponseEntity<StatsResponseDTO> addStats(@Valid @RequestBody StatsRequestDTO statsDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statsService.addStats(statsDto));
    }

    @PutMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<StatsResponseDTO> updateStats(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody StatsRequestDTO statsDto
    ) {
        return ResponseEntity.ok(statsService.updateStats(id, statsDto));
    }

    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<Void> deleteStats(@PathVariable(PARAM_ID) long id) {
        statsService.deleteStatsById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStatsByBillId(@RequestParam(PARAM_BILL_ID) long billId) {
        statsService.deleteStatsByBillId(billId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTotalPriceOfStatsByBillId(
            @RequestParam(PARAM_BILL_ID) long billId,
            @RequestParam(PARAM_PRICE) Double price
    ) {
        statsService.updateTotalPriceOfStatsByBillId(billId, price);
        return ResponseEntity.ok().build();
    }
}
