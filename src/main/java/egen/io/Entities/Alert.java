package egen.io.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.UUID;
import egen.io.Entities.Alert;

@Entity

public class Alert {
    @Id
    String id;
    String vin;
    String timeStamp;
    String alertReason;
    String alertType;

    public Alert(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }



    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAlertReason() {
        return alertReason;
    }

    public void setAlertReason(String alertReason) {
        this.alertReason = alertReason;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }



}
