package kh.farrukh.common.paging;

import kh.farrukh.common.exceptions.exceptions.BadRequestException;

public class PageChecker {

    public static void checkPageNumber(int page) {
        if (page < 1) {
            throw new BadRequestException("Page index");
        }
    }
}
