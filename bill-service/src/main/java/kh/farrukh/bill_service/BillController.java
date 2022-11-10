package kh.farrukh.bill_service;

import kh.farrukh.bill_service.payloads.BillRequestDTO;
import kh.farrukh.bill_service.payloads.BillResponseDTO;
import kh.farrukh.bill_service.payloads.BillWithStatsResponseDTO;
import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.bill.StatsIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.bill_service.Constants.*;

@RestController
@RequestMapping(ENDPOINT_BILL)
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @GetMapping
    public ResponseEntity<PagingResponse<BillResponseDTO>> getBills(
            @RequestParam(name = "owner_id", required = false) Long ownerId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(billService.getBills(ownerId, page, pageSize));
    }

    @GetMapping("with-stats")
    public ResponseEntity<PagingResponse<BillWithStatsResponseDTO>> getBillsWithStats(
            @RequestParam(name = "owner_id", required = false) Long ownerId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(billService.getBillsWithStats(ownerId, page, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<BillResponseDTO> getBillById(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(billService.getBillById(id));
    }

    @PostMapping
    public ResponseEntity<BillResponseDTO> addBill(
            @Valid @RequestBody BillRequestDTO billDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billService.addBill(billDto));
    }

    @PostMapping(ENDPOINT_POSTFIX_ADD_STATS_TO_BILL)
    public ResponseEntity<BillResponseDTO> addStatsToBill(
            @PathVariable long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    ) {
        return ResponseEntity.ok(billService.addStatsToBill(id, statsIdDTO));
    }

    @PostMapping(ENDPOINT_POSTFIX_DELETE_STATS_FROM_BILL)
    public ResponseEntity<BillResponseDTO> deleteStatsFromBill(
            @PathVariable long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    ) {
        return ResponseEntity.ok(billService.deleteStatsFromBill(id, statsIdDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<BillResponseDTO> updateBill(
            @PathVariable long id,
            @Valid @RequestBody BillRequestDTO billDto
    ) {
        return ResponseEntity.ok(billService.updateBill(id, billDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBill(
            @PathVariable long id
    ) {
        billService.deleteBillById(id);
        return ResponseEntity.noContent().build();
    }
}
