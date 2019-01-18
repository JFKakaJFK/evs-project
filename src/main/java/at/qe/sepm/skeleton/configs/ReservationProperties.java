package at.qe.sepm.skeleton.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("reservation")
public class ReservationProperties {

    /**
     * Timeframe in which an overdue reservation should be
     * WAR file for production, see https://stackoverflow.com/questions/8885201/uploaded-image-only-available-after-refreshing-the-page
     */
    private static long overdueBuffer = 2 * 24 * 60 * 60 * 1000; // 2 Days

    private static long nextReservationBuffer = 30 * 60 * 1000; // 30 Minutes

    public static long getOverdueBuffer() {
        return overdueBuffer;
    }

    public static long getNextReservationBuffer() {
        return nextReservationBuffer;
    }
}

