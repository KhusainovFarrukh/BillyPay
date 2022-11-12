package kh.farrukh.feign_clients.stats;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.stats.payloads.StatsRequestDTO;
import kh.farrukh.feign_clients.stats.payloads.StatsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static kh.farrukh.feign_clients.stats.Constants.*;

@FeignClient(name = "stats-service", path = ENDPOINT_STATS)
public interface StatsClient {

    @GetMapping
    PagingResponse<StatsResponseDTO> getStatsList(
            @RequestParam(name = PARAM_BILL_ID, required = false) Long billId,
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    );

    @GetMapping(ENDPOINT_POSTFIX_OF_BILL)
    List<StatsResponseDTO> getAllStatsOfBill(@RequestParam(name = PARAM_BILL_ID) Long billId);

    @GetMapping(ENDPOINT_POSTFIX_ID)
    StatsResponseDTO getStatsById(@PathVariable(PARAM_ID) long id);

    @PostMapping
    StatsResponseDTO addStats(@Valid @RequestBody StatsRequestDTO statsDto);

    @PutMapping(ENDPOINT_POSTFIX_ID)
    StatsResponseDTO updateStats(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody StatsRequestDTO statsDto
    );

    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    void deleteStats(@PathVariable(PARAM_ID) long id);

    @DeleteMapping
    void deleteStatsByBillId(@RequestParam(PARAM_BILL_ID) long billId);

    @PutMapping
    void updateTotalPriceOfStatsByBillId(
            @RequestParam(PARAM_BILL_ID) long billId,
            @RequestParam(PARAM_PRICE) Double price
    );
}
