package kh.farrukh.stats.utils.checkers;

import kh.farrukh.stats.StatsRepository;
import kh.farrukh.stats.utils.exception.custom.exceptions.BadRequestException;
import kh.farrukh.stats.utils.exception.custom.exceptions.ResourceNotFoundException;

/**
 * It contains static methods that check if a resource exists in the database,
 * is unique, request parameter is valid and etc.
 */
public class Checkers {

    public static void checkPageNumber(int page) {
        if (page < 1) {
            throw new BadRequestException("Page index");
        }
    }

    public static void checkStatsId(StatsRepository statsRepository, long id) {
        if (!statsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bill", "id", id);
        }
    }
}
