import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenerarDatosGPS {

    public static void generarCSV(String filename) {
        List<GPSData> datos = new ArrayList<>();
        LocalDateTime inicio = LocalDateTime.of(2025, 3, 25, 8, 0);

        datos.addAll(GPSDataSimulator.simularDatos("BUS01", inicio, 60));
        datos.addAll(GPSDataSimulator.simularDatos("BUS02", inicio, 60));
        datos.addAll(GPSDataSimulator.simularDatos("BUS03", inicio, 60));

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("busId,timestamp,latitude,longitude,speed\n");
            for (GPSData data : datos) {
                writer.write(data.toString() + "\n");
            }
            System.out.println("Datos generados en: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
