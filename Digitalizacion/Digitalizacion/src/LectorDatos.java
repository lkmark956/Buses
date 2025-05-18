import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.Collectors;

public class LectorDatos {

    public static List<GPSData> leerCSV(String path) {
        List<GPSData> lista = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        try {
            List<String> lineas = Files.readAllLines(Paths.get(path));
            for (int i = 1; i < lineas.size(); i++) {
                String[] tokens = lineas.get(i).split(",");
                if (tokens.length == 5) {
                    String busId = tokens[0];
                    LocalDateTime timestamp = LocalDateTime.parse(tokens[1], formatter);
                    double lat = Double.parseDouble(tokens[2]);
                    double lon = Double.parseDouble(tokens[3]);
                    double speed = Double.parseDouble(tokens[4]);

                    if (lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180 && speed >= 0)
                        lista.add(new GPSData(busId, timestamp, lat, lon, speed));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static String analizarDatosTexto(List<GPSData> datos) {
        StringBuilder sb = new StringBuilder();
        Map<String, List<GPSData>> porBus = datos.stream().collect(Collectors.groupingBy(GPSData::getBusId));

        for (String busId : porBus.keySet()) {
            List<GPSData> registros = porBus.get(busId);
            if (registros.isEmpty()) continue;

            double totalSpeed = registros.stream().mapToDouble(GPSData::getSpeed).sum();
            long paradas = registros.stream().filter(d -> d.getSpeed() == 0).count();
            double velocidadMedia = totalSpeed / registros.size();

            sb.append("Bus ").append(busId).append("\n");
            sb.append(String.format("  Velocidad media: %.2f km/h\n", velocidadMedia));
            sb.append("  Paradas detectadas: ").append(paradas).append("\n");
            sb.append("  Última posición: ")
                    .append(String.format("%.5f", registros.get(registros.size() - 1).getLatitude()))
                    .append(", ")
                    .append(String.format("%.5f", registros.get(registros.size() - 1).getLongitude()))
                    .append("\n\n");
        }

        return sb.toString();
    }

    public static void exportarJSONUltimaPosicion(List<GPSData> datos) {
        Map<String, GPSData> ultima = new HashMap<>();

        for (GPSData data : datos) {
            ultima.put(data.getBusId(), data); // sobreescribe => última
        }

        for (Map.Entry<String, GPSData> entry : ultima.entrySet()) {
            GPSData d = entry.getValue();
            String filename = entry.getKey().toLowerCase() + "_status.json";
            try (FileWriter fw = new FileWriter(filename)) {
                fw.write("{\n");
                fw.write("  \"busId\": \"" + d.getBusId() + "\",\n");
                fw.write("  \"latitude\": " + d.getLatitude() + ",\n");
                fw.write("  \"longitude\": " + d.getLongitude() + ",\n");
                fw.write("  \"timestamp\": \"" + d.getTimestamp() + "\"\n");
                fw.write("}");
                System.out.println("Exportado JSON: " + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
