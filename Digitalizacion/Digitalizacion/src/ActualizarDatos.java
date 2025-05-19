import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class ActualizarDatos {

    public static void actualizarVelocidad(List<GPSData> datos) {
        Set<String> busesParaActualizar = new HashSet<>();
        busesParaActualizar.add("BUS01");
        busesParaActualizar.add("BUS02");
        busesParaActualizar.add("BUS03");

        Random rand = new Random();

        // Generar velocidades distintas para cada bus
        Map<String, Double> deltas = new HashMap<>();
        for (String busId : busesParaActualizar) {
            // delta entre -5 y +5
            deltas.put(busId, -5 + 10 * rand.nextDouble());
        }

        for (int i = 0; i < datos.size(); i++) {
            GPSData d = datos.get(i);
            if (busesParaActualizar.contains(d.getBusId())) {
                double deltaVel = deltas.get(d.getBusId());
                double nuevaVel = d.getSpeed() + deltaVel;
                if (nuevaVel < 0) nuevaVel = 0; // Para que no exista una velocidad negativa

                GPSData actualizado = new GPSData(d.getBusId(), d.getTimestamp(), d.getLatitude(), d.getLongitude(), nuevaVel);
                datos.set(i, actualizado);
            }
        }
    }
}
