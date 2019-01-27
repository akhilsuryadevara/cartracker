package egen.io.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import egen.io.Entities.Alert;
import egen.io.Exception.ResourceNotFoundException;

import egen.io.repository.AlertsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class AlertsServiceImplementation implements AlertsService {

    @Autowired
    AlertsRepository alertsRepository;

    @Transactional
    public void insertAlert(Alert alert){
        alertsRepository.save(alert);
    }
    @Transactional
    public String getAllAlerts(){
        List<Alert> alerts = (List<Alert>) alertsRepository.findAll();

        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.writeValueAsString(alerts);
        }
        catch(IOException e){
            throw new ResourceNotFoundException("Values in Database are not in valid format");

        }

    }
    @Transactional
    public String getAlertsofVehicle(String vin) {
        List<Alert> alerts = (List<Alert>) alertsRepository.findAllById(Collections.singleton(vin));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(alerts);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException("Alerts data is not in valid format");
        }
    }

}
