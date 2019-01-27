package egen.io.Service;

import egen.io.Entities.Alert;

public interface AlertsService{
    void insertAlert(Alert alert);
    public String getAllAlerts();
    String getAlertsofVehicle(String vin);
}
