package egen.io.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import egen.io.Entities.Vehicle;
import egen.io.Exception.BadRequestException;
import egen.io.Exception.ResourceNotFoundException;
import egen.io.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImplementation implements VehicleService {
    @Autowired
    VehiclesRepository vehiclesRepository;

    @Transactional
    public void upsertVehicles(String vehicles){
        ObjectMapper mapper = new ObjectMapper();
        try{
            Vehicle[] vehicleArr = mapper.readValue(vehicles, Vehicle[].class);
            for(Vehicle v: vehicleArr){
                vehiclesRepository.save(v);
            }
        }
        catch(IOException e){
            throw new BadRequestException("Please Check: The Vehicles Data is not a valid format");
        }
    }
    @Transactional
    public String getAllVehicles(){
        List<Vehicle> vehicles = (List<Vehicle>) vehiclesRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.writeValueAsString(vehicles);
        }
        catch(IOException e){
            throw new ResourceNotFoundException("Values in Database are not in valid format");

        }
    }
    @Transactional
    public Vehicle getOneVehicle(String vin){
        Optional<Vehicle> vehicle = vehiclesRepository.findById(vin);
        if(!vehicle.isPresent()){
            throw new BadRequestException("Vehicle with id " + vin + "doesn't exist");
        }
        return vehicle.get();

    }



}
