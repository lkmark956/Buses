import java.time.LocalDateTime;

public class GPSData {
    private String busId;
    private LocalDateTime timestamp;
    private double latitude;
    private double longitude;
    private double speed;

    public GPSData(String busId, LocalDateTime timestamp, double latitude, double longitude, double speed) {
        this.busId = busId;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public String getBusId() { return busId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getSpeed() { return speed; }

    @Override
    public String toString() {
        return busId + "," + timestamp + "," + latitude + "," + longitude + "," + speed;
    }
}
