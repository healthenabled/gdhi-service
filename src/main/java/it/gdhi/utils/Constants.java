package it.gdhi.utils;

public class Constants {
    public static final String SCORE_DESCRIPTION_NOT_AVAILABLE="Missing or Not Available";
    public static final String MIME_TYPE = "application/octet-stream";
    public static final String HEADER_KEY = "Content-Disposition";
    public static final String COUNTRY_NAME = "Country Name";
    public static final String PHASE = "Phase ";
    public static final String CATEGORY = "Category ";
    public static final String INDICATOR = "Indicator ";
    public static final String OVERALL_PHASE = "Overall Phase";

    public static final String NEW_STATUS = "NEW";

    public static class Mail {
        public static final String SUBJECT = "GDHI has a new response from %s";
        public static final String HEALTH_INDICATOR_PATH = "%s/health_indicator_questionnaire/%s/review";
        public static final String BODY = "Hello %s  ,\n" +
                "%s, %s has provided response for %s, in the GDHI website.\n" +
                "Contact: %s \n" +
                "For more details visit: %s \n" +
                "Regards \n" +
                "GDHI Team ";
    }
}
