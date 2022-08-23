package kh.farrukh.bill;

public class Constants {

    public static final String ENDPOINT_BILL = "/api/v1/bills";
    public static final String ENDPOINT_POSTFIX_ADD_STATS_TO_BILL = "{id}/add-stats";
    public static final String ENDPOINT_POSTFIX_DELETE_STATS_FROM_BILL = "{id}/delete-stats";
    public static final String ENDPOINT_ADD_STATS_TO_BILL = ENDPOINT_BILL + ENDPOINT_POSTFIX_ADD_STATS_TO_BILL;
    public static final String ENDPOINT_DELETE_STATS_FROM_BILL = ENDPOINT_BILL + ENDPOINT_POSTFIX_DELETE_STATS_FROM_BILL;
    public static final String SEQUENCE_NAME_BILL_ID = "bill_id_seq";
    public static final String TABLE_NAME_BILL = "bill";
    public static final String GENERATOR_NAME_BILL_ID = "bill_id_generator";

}
