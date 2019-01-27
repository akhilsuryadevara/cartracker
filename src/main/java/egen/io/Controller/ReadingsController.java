package egen.io.Controller;

import egen.io.Service.ReadingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/readings")
public class ReadingsController {

    @Autowired
    ReadingsService service;

    @RequestMapping(method= RequestMethod.POST)
    void insertReadings(@RequestBody String readings){
        service.insertReadings(readings);
    }

    @RequestMapping(method = RequestMethod.GET, path ="/{vin}" )
    String getVehicleReadings(@PathVariable("vin") String vin){
        return service.getVehicleReadings(vin);
    }

}
