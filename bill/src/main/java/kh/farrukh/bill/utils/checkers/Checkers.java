package kh.farrukh.bill.utils.checkers;

import kh.farrukh.bill.BillRepository;
import kh.farrukh.bill.utils.exception.custom.exceptions.BadRequestException;
import kh.farrukh.bill.utils.exception.custom.exceptions.ResourceNotFoundException;

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

    public static void checkBillId(BillRepository billRepository, long id) {
        if (!billRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bill", "id", id);
        }
    }
}
