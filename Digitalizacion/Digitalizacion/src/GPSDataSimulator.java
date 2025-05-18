import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GPSDataSimulator {
    private static final Random random = new Random();

    public static List<GPSData> simularDatos(String busId, LocalDateTime startTime, int minutos) {
        List<GPSData> lista = new ArrayList<>();
        double lat = 40.4168;
        double lon = -3.7038;

        for (int i = 0; i < minutos; i++) {
            LocalDateTime timestamp = startTime.plusMinutes(i);
            double speed = random.nextDouble() * 50; // 0 - 50 km/h
            if (random.nextDouble() < 0.1) speed = 0; // 10% chance de parada

            lat += (random.nextDouble() - 0.5) / 1000;
            lon += (random.nextDouble() - 0.5) / 1000;

            lista.add(new GPSData(busId, timestamp, lat, lon, speed));
        }
        return lista;
    }
}
