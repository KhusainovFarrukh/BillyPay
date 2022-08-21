package kh.farrukh.clients.stats;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stats")
public interface StatsClient {

    // TODO: 8/20/22 use ENDPOINT_ constant
    @DeleteMapping("api/v1/stats")
    ResponseEntity<Void> deleteStatsByBillId(@RequestParam("bill_id") long billId);

    // TODO: 8/20/22 use ENDPOINT_ constant
    @PutMapping("api/v1/stats")
    ResponseEntity<Void> updateTotalPriceOfStatsByBillId(
            @RequestParam("bill_id") long billId,
            @RequestParam("price") Double price
    );
}
