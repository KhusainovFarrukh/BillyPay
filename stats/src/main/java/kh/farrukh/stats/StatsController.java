package kh.farrukh.stats;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.stats.payloads.StatsRequestDTO;
import kh.farrukh.stats.payloads.StatsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static kh.farrukh.stats.Constants.ENDPOINT_STATS;

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
        return new ResponseEntity<>(statsService.getStatsList(billId, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("of-bill")
    public ResponseEntity<List<StatsResponseDTO>> getAllStatsOfBill(
            @RequestParam(name = "bill_id") long billId
    ) {
        return new ResponseEntity<>(statsService.getAllStatsOfBill(billId), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StatsResponseDTO> getStatsById(@PathVariable long id) {
        return new ResponseEntity<>(statsService.getStatsById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StatsResponseDTO> addStats(@Valid @RequestBody StatsRequestDTO statsDto) {
        return new ResponseEntity<>(statsService.addStats(statsDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<StatsResponseDTO> updateStats(
            @PathVariable long id,
            @Valid @RequestBody StatsRequestDTO statsDto
    ) {
        return new ResponseEntity<>(statsService.updateStats(id, statsDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStats(@PathVariable long id) {
        statsService.deleteStatsById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStatsByBillId(@RequestParam("bill_id") long billId) {
        statsService.deleteStatsByBillId(billId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> updateTotalPriceOfStatsByBillId(
            @RequestParam("bill_id") long billId,
            @RequestParam("price") Double price
    ) {
        statsService.updateTotalPriceOfStatsByBillId(billId, price);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
