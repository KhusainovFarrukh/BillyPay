package kh.farrukh.stats_service;

import kh.farrukh.feign_clients.stats.payloads.StatsRequestDTO;
import kh.farrukh.feign_clients.stats.payloads.StatsResponseDTO;
import org.springframework.beans.BeanUtils;

public class StatsMappers {

    public static StatsResponseDTO toStatsResponseDTO(Stats stats) {
        StatsResponseDTO statsResponseDTO = new StatsResponseDTO();
        BeanUtils.copyProperties(stats, statsResponseDTO);
        return statsResponseDTO;
    }

    public static Stats toStats(StatsRequestDTO statsRequestDTO, Double price) {
        Stats stats = new Stats();
        BeanUtils.copyProperties(statsRequestDTO, stats);
        stats.setTotalPrice(statsRequestDTO.getAmount() * price);
        return stats;
    }
}
