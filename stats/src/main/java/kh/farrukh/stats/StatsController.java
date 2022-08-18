package kh.farrukh.stats;

import kh.farrukh.stats.utils.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.stats.Constants.ENDPOINT_STATS;

@RestController
@RequestMapping(ENDPOINT_STATS)
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<PagingResponse<Stats>> getStatsList(
            @RequestParam(name = "bill_id", required = false) Long billId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize
    ) {
        return new ResponseEntity<>(statsService.getStatsList(billId, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Stats> getStatsById(@PathVariable long id) {
        return new ResponseEntity<>(statsService.getStatsById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Stats> addStats(@Valid @RequestBody StatsDTO statsDto) {
        return new ResponseEntity<>(statsService.addStats(statsDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Stats> updateStats(
            @PathVariable long id,
            @Valid @RequestBody StatsDTO statsDto
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
}
