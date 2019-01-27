package egen.io.Service;

public interface ReadingsService {
    void insertReadings(String readings);
    String getVehicleReadings(String vin);
}
