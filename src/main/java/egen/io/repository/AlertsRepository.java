package egen.io.repository;

import egen.io.Entities.Alert;
import egen.io.Entities.Reading;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlertsRepository extends CrudRepository<Alert,String> {
   // List<Reading> getReadingsofVehicle(String vin);
    //Iterator<VehicleAlerts> getAllAlerts();
    //List<Alert> getAlertsofVehicle(String vin);
    //void insertAlert(Alert alert);
}
