package egen.io.Controller;

import egen.io.Service.AlertsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alerts")
@Api(description = "you get the alerts here")
public class AlertsController {
    @Autowired
    AlertsService alertsService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get all the Alerts from here")
    @ApiResponses(value = {
            @ApiResponse(code = 200 , message ="Okayish"),
            @ApiResponse(code = 404 , message = "Not Found"),
            @ApiResponse(code = 500 , message = "Internal Server Error")

    })
    public String getAllAlerts(){return alertsService.getAllAlerts();}


    @RequestMapping(method = RequestMethod.GET, path = "/{vin}")
    public String getAlertsofVehicle(@ApiParam(value="id of the vehicle",required = true) @PathVariable String vin){
        return alertsService.getAlertsofVehicle(vin);
    }

}
