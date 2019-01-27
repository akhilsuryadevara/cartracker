package egen.io.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import egen.io.Entities.Reading;
import egen.io.Entities.Tires;
import egen.io.Entities.Vehicle;
import egen.io.Entities.Alert;
import egen.io.Exception.BadRequestException;
import egen.io.Exception.ResourceNotFoundException;
import egen.io.repository.ReadingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class ReadingServiceImplementation implements ReadingsService {

    @Autowired
    ReadingsRepository readingsRepository;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    AlertsService alertsService;

    @Transactional
    public void insertReadings(String readings) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Reading reading = mapper.readValue(readings, Reading.class);
            Vehicle vehicle = vehicleService.getOneVehicle(reading.getVin());

            if (vehicle != null) {

                checkAlerts(reading, vehicle);
                readingsRepository.save(reading);
            }

        } catch (IOException e) {
            throw new BadRequestException("The Readings data is not in a valid format");
        }


    }
    @Transactional
    public String getVehicleReadings(String vin){
        Optional<Reading> readings = readingsRepository.findById(vin);
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.writeValueAsString(readings);
        }
        catch(JsonProcessingException e){
            throw new ResourceNotFoundException("Values in Database are not in valid format");
        }
    }

    @Async
    public void checkAlerts(Reading reading,Vehicle vehicle){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String date = df.format(new Date());
        Tires tires = reading.getTires();
        int[] tirePressures = { tires.getFrontLeft(),tires.getFrontRight(),tires.getRearLeft(),tires.getRearRight()};
        if(reading.getEngineRpm() > vehicle.getRedlineRpm()){
            Alert alert = new Alert();
            alert.setVin(vehicle.getVin());
            alert.setTimeStamp(date);
            alert.setAlertReason("HIGH RPM");
            alert.setAlertType("HIGH");
            //mailService.sendEmail(alert);
            alertsService.insertAlert(alert);
        }

        if(reading.getFuelVolume()<0.1* vehicle.getMaxFuelVolume()){
            Alert alert = new Alert();
            alert.setVin(vehicle.getVin());
            alert.setTimeStamp(date);
            alert.setAlertType("MEDIUM");
            alert.setAlertReason("Low Fuel Volume");
            alertsService.insertAlert(alert);
        }

        if(reading.isEngineCoolantLow() || reading.isCheckEngineLightOn()){
            Alert alert =  new Alert();
            alert.setVin(vehicle.getVin());
            alert.setTimeStamp(date);
            alert.setAlertType("LOW");
            if (reading.isEngineCoolantLow())
                alert.setAlertReason("Engine Coolant Low");
            else
                alert.setAlertReason("Check Engine Light On");
            alertsService.insertAlert(alert);
        }

        for(int pressure : tirePressures){
            if(pressure<32 || pressure>36){
                Alert alert = new Alert();
                alert.setVin(vehicle.getVin());
                alert.setAlertType("LOW");
                if(pressure<32){
                    alert.setAlertReason("Low Tire Pressure");
                }
                else
                    alert.setAlertReason("High Tire Pressure");
                alertsService.insertAlert(alert);
            }

        }
    }
}
