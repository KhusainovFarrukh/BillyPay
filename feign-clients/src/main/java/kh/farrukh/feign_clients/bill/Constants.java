package kh.farrukh.feign_clients.bill;

public class Constants {

    public static final String ENDPOINT_BILL = "/api/v1/bills";
    public static final String ENDPOINT_POSTFIX_ID = "{id}";
    public static final String ENDPOINT_POSTFIX_ADD_STATS_TO_BILL = ENDPOINT_POSTFIX_ID + "/add-stats";
    public static final String ENDPOINT_POSTFIX_DELETE_STATS_FROM_BILL = ENDPOINT_POSTFIX_ID + "/delete-stats";
    public static final String ENDPOINT_POSTFIX_GET_BILLS_WITH_STATS = "with-stats";

    public static final String PARAM_ID = "id";
    public static final String PARAM_OWNER_ID = "owner_id";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PAGE_SIZE = "page_size";

}
