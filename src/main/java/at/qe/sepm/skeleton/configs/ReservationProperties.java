package at.qe.sepm.skeleton.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("reservation")
public class ReservationProperties {

    /**
     * Timeframe in which an overdue reservation should be
     * WAR file for production, see https://stackoverflow.com/questions/8885201/uploaded-image-only-available-after-refreshing-the-page
     */
    private long overdueBuffer = 2 * 24 * 60 * 60 * 1000; // 2 Days

    private long nextReservationBuffer = 30 * 60 * 1000; // 30 Minutes

    public long getOverdueBuffer() {
        return overdueBuffer;
    }

    public void setOverdueBuffer(long overdueBuffer) {
        this.overdueBuffer = overdueBuffer;
    }

    public long getNextReservationBuffer() {
        return nextReservationBuffer;
    }

    public void setNextReservationBuffer(long nextReservationBuffer) {
        this.nextReservationBuffer = nextReservationBuffer;
    }
}

