package kh.farrukh.bill_service;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.bill.payloads.BillRequestDTO;
import kh.farrukh.feign_clients.bill.payloads.BillResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.BillWithStatsResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.StatsIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.bill.BillConstants.*;

@RestController
@RequestMapping(ENDPOINT_BILL)
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @GetMapping
    public ResponseEntity<PagingResponse<BillResponseDTO>> getBills(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestParam(name = PARAM_OWNER_ID, required = false) Long ownerId,
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(billService.getBills(token, ownerId, page, pageSize));
    }

    @GetMapping(ENDPOINT_POSTFIX_GET_BILLS_WITH_STATS)
    public ResponseEntity<PagingResponse<BillWithStatsResponseDTO>> getBillsWithStats(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestParam(name = PARAM_OWNER_ID, required = false) Long ownerId,
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(billService.getBillsWithStats(token, ownerId, page, pageSize));
    }

    @GetMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<BillResponseDTO> getBillById(
            @PathVariable(PARAM_ID) long id
    ) {
        return ResponseEntity.ok(billService.getBillById(id));
    }

    @PostMapping
    public ResponseEntity<BillResponseDTO> addBill(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Valid @RequestBody BillRequestDTO billDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billService.addBill(token, billDto));
    }

    @PostMapping(ENDPOINT_POSTFIX_ADD_STATS_TO_BILL)
    public ResponseEntity<BillResponseDTO> addStatsToBill(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    ) {
        return ResponseEntity.ok(billService.addStatsToBill(id, statsIdDTO));
    }

    @PostMapping(ENDPOINT_POSTFIX_DELETE_STATS_FROM_BILL)
    public ResponseEntity<BillResponseDTO> deleteStatsFromBill(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    ) {
        return ResponseEntity.ok(billService.deleteStatsFromBill(id, statsIdDTO));
    }

    @PutMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<BillResponseDTO> updateBill(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody BillRequestDTO billDto
    ) {
        return ResponseEntity.ok(billService.updateBill(id, billDto));
    }

    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<Void> deleteBill(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable(PARAM_ID) long id
    ) {
        billService.deleteBillById(token, id);
        return ResponseEntity.noContent().build();
    }
}
