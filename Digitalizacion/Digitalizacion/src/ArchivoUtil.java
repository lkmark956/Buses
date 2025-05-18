import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArchivoUtil {

    public static void guardarCSV(String filename, List<GPSData> datos) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("busId,timestamp,latitude,longitude,speed\n");
            for (GPSData data : datos) {
                writer.write(data.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

