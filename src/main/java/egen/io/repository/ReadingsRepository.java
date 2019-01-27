package egen.io.repository;

import egen.io.Entities.Reading;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReadingsRepository extends CrudRepository<Reading,String> {

    //void insertReadings(Reading reading);
    //List<Reading> getReadingsofVehicle(String vin);
}
