package egen.io.repository;

import egen.io.Entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehiclesRepository extends CrudRepository<Vehicle,String> {
   // void upsertVehicles(Vehicle vehicle);

    //Vehicle getVehicle(String vin);

    //List<Vehicle> getAllVehicles();

}
