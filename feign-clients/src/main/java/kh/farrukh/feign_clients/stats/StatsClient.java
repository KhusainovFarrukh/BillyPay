package kh.farrukh.feign_clients.stats;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "stats-service")
public interface StatsClient {

    // TODO: 8/20/22 use ENDPOINT_ constant
    @GetMapping("api/v1/stats/of-bill")
    List<Stats> getAllStatsOfBill(
            @RequestParam(name = "bill_id") Long billId
    );

    // TODO: 8/20/22 use ENDPOINT_ constant
    @DeleteMapping("api/v1/stats")
    void deleteStatsByBillId(@RequestParam("bill_id") long billId);

    // TODO: 8/20/22 use ENDPOINT_ constant
    @PutMapping("api/v1/stats")
    void updateTotalPriceOfStatsByBillId(
            @RequestParam("bill_id") long billId,
            @RequestParam("price") Double price
    );
}
