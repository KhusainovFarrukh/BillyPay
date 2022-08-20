package kh.farrukh.clients.bill;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(
        name = "bill",
        url = "${clients.bill.url}"
)
public interface BillClient {

    // TODO: 8/20/22 use ENDPOINT_ constant

    @GetMapping("api/v1/bills/{id}")
    Bill getBillById(
            @PathVariable("id") long id
    );

    @PostMapping("api/v1/bills/{id}/add-stats")
    Bill addStatsToBill(
            @PathVariable("id") long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    );

    @PostMapping("api/v1/bills/{id}/delete-stats")
    Bill deleteStatsFromBill(
            @PathVariable("id") long id,
            @Valid @RequestBody StatsIdDTO statsIdDTO
    );
}
