package kh.farrukh.bill;

import kh.farrukh.bill.payloads.BillRequestDTO;
import kh.farrukh.bill.payloads.BillResponseDTO;
import kh.farrukh.bill.payloads.BillWithStatsResponseDTO;
import kh.farrukh.clients.bill.StatsIdDTO;
import kh.farrukh.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.bill.Constants.*;

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
        return new ResponseEntity<>(billService.getBills(ownerId, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("with-stats")
    public ResponseEntity<PagingResponse<BillWithStatsResponseDTO>> getBillsWithStats(
            @RequestParam(name = "owner_id", required = false) Long ownerId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize
    ) {
        return new ResponseEntity<>(billService.getBillsWithStats(ownerId, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<BillResponseDTO> getBillById(
            @PathVariable long id
    ) {
        return new ResponseEntity<>(billService.getBillById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BillResponseDTO> addBill(
            @Valid @RequestBody BillRequestDTO billDto
    ) {
        return new ResponseEntity<>(billService.addBill(billDto), HttpStatus.CREATED);
    }

    @PostMapping(ENDPOINT_POSTFIX_ADD_STATS_TO_BILL)
    public ResponseEntity<BillResponseDTO> addStatsToBill(
            @PathVariable long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    ) {
        return new ResponseEntity<>(billService.addStatsToBill(id, statsIdDTO), HttpStatus.OK);
    }

    @PostMapping(ENDPOINT_POSTFIX_DELETE_STATS_FROM_BILL)
    public ResponseEntity<BillResponseDTO> deleteStatsFromBill(
            @PathVariable long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    ) {
        return new ResponseEntity<>(billService.deleteStatsFromBill(id, statsIdDTO), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<BillResponseDTO> updateBill(
            @PathVariable long id,
            @Valid @RequestBody BillRequestDTO billDto
    ) {
        return new ResponseEntity<>(billService.updateBill(id, billDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBill(
            @PathVariable long id
    ) {
        billService.deleteBillById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
