package kh.farrukh.feign_clients.bill;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.bill.payloads.BillRequestDTO;
import kh.farrukh.feign_clients.bill.payloads.BillResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.BillWithStatsResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.StatsIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.bill.BillConstants.*;

@FeignClient(name = "bill-service", path = ENDPOINT_BILL)
public interface BillClient {

    @GetMapping
    PagingResponse<BillResponseDTO> getBills(
            @RequestParam(name = PARAM_OWNER_ID, required = false) Long ownerId,
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    );

    @GetMapping(ENDPOINT_POSTFIX_GET_BILLS_WITH_STATS)
    PagingResponse<BillWithStatsResponseDTO> getBillsWithStats(
            @RequestParam(name = PARAM_OWNER_ID, required = false) Long ownerId,
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    );

    @GetMapping(ENDPOINT_POSTFIX_ID)
    BillResponseDTO getBillById(@PathVariable(PARAM_ID) long id);

    @PostMapping
    BillResponseDTO addBill(@Valid @RequestBody BillRequestDTO billDto);

    @PostMapping(ENDPOINT_POSTFIX_ADD_STATS_TO_BILL)
    BillResponseDTO addStatsToBill(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    );

    @PostMapping(ENDPOINT_POSTFIX_DELETE_STATS_FROM_BILL)
    BillResponseDTO deleteStatsFromBill(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    );

    @PutMapping(ENDPOINT_POSTFIX_ID)
    BillResponseDTO updateBill(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody BillRequestDTO billDto
    );

    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    void deleteBill(@PathVariable(PARAM_ID) long id);
}
