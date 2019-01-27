package egen.io.Controller;

import egen.io.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/vehicles")
public class VehiclesController {

    @Autowired
    VehicleService vehicleService;
    @RequestMapping(method = RequestMethod.PUT)
    public void upsertVehicles(@RequestBody String vehicles){
        vehicleService.upsertVehicles(vehicles);


    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

}
