package egen.io.Service;

import egen.io.Entities.Vehicle;

public interface VehicleService {
    void upsertVehicles(String vehicles);
    String getAllVehicles();
    Vehicle getOneVehicle(String vin);
}
