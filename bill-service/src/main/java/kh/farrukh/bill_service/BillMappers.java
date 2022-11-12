package kh.farrukh.bill_service;

import kh.farrukh.feign_clients.bill.payloads.BillRequestDTO;
import kh.farrukh.feign_clients.bill.payloads.BillResponseDTO;
import kh.farrukh.feign_clients.bill.payloads.BillTypeDTO;
import kh.farrukh.feign_clients.bill.payloads.BillWithStatsResponseDTO;
import kh.farrukh.feign_clients.stats.payloads.StatsResponseDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class BillMappers {

    public static BillType toBillType(BillTypeDTO billTypeDTO) {
        return BillType.valueOf(billTypeDTO.name());
    }

    public static BillTypeDTO toBillTypeDTO(BillType billType) {
        return BillTypeDTO.valueOf(billType.name());
    }

    public static BillResponseDTO toBillResponseDTO(Bill bill) {
        BillResponseDTO billResponseDTO = new BillResponseDTO();
        BeanUtils.copyProperties(bill, billResponseDTO);
        billResponseDTO.setType(toBillTypeDTO(bill.getType()));
        return billResponseDTO;
    }

    public static BillWithStatsResponseDTO toBillWithStatsResponseDTO(Bill bill, List<StatsResponseDTO> stats) {
        BillWithStatsResponseDTO billWithStatsResponseDTO = new BillWithStatsResponseDTO();
        BeanUtils.copyProperties(bill, billWithStatsResponseDTO);
        billWithStatsResponseDTO.setType(toBillTypeDTO(bill.getType()));
        billWithStatsResponseDTO.setStats(stats);
        return billWithStatsResponseDTO;
    }

    public static Bill toBill(BillRequestDTO billRequestDTO) {
        Bill bill = new Bill();
        BeanUtils.copyProperties(billRequestDTO, bill);
        bill.setType(toBillType(billRequestDTO.getType()));
        return bill;
    }
}
