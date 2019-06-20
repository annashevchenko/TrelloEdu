package fw.pages;

public class ReportDataCollector {

    private static volatile String reportMessage = "";

    public ReportDataCollector() {
    }

    public static synchronized String getReportMessage() {
        return reportMessage;
    }

    public static synchronized void clear() {
        reportMessage = "";
    }

    public static synchronized void append(String m) {
        reportMessage = reportMessage + m;
    }


}
