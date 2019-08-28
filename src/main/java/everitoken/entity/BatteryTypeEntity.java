package everitoken.entity;

import javax.persistence.*;

@Entity
@Table(name = "battery_type", schema = "everitoken", catalog = "")
public class BatteryTypeEntity {
    private Integer uid;
    private String designedMaxCapacity;
    private String designedAverageTemperature;
    private String designedAverageVoltage;
    private String batteryMaterial;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "designed_max_capacity")
    public String getDesignedMaxCapacity() {
        return designedMaxCapacity;
    }

    public void setDesignedMaxCapacity(String designedMaxCapacity) {
        this.designedMaxCapacity = designedMaxCapacity;
    }

    @Basic
    @Column(name = "designed_average_temperature")
    public String getDesignedAverageTemperature() {
        return designedAverageTemperature;
    }

    public void setDesignedAverageTemperature(String designedAverageTemperature) {
        this.designedAverageTemperature = designedAverageTemperature;
    }

    @Basic
    @Column(name = "designed_average_voltage")
    public String getDesignedAverageVoltage() {
        return designedAverageVoltage;
    }

    public void setDesignedAverageVoltage(String designedAverageVoltage) {
        this.designedAverageVoltage = designedAverageVoltage;
    }

    @Basic
    @Column(name = "battery_material")
    public String getBatteryMaterial() {
        return batteryMaterial;
    }

    public void setBatteryMaterial(String batteryMaterial) {
        this.batteryMaterial = batteryMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatteryTypeEntity that = (BatteryTypeEntity) o;

        if (uid != that.uid) return false;
        if (designedMaxCapacity != null ? !designedMaxCapacity.equals(that.designedMaxCapacity) : that.designedMaxCapacity != null)
            return false;
        if (designedAverageTemperature != null ? !designedAverageTemperature.equals(that.designedAverageTemperature) : that.designedAverageTemperature != null)
            return false;
        if (designedAverageVoltage != null ? !designedAverageVoltage.equals(that.designedAverageVoltage) : that.designedAverageVoltage != null)
            return false;
        if (batteryMaterial != null ? !batteryMaterial.equals(that.batteryMaterial) : that.batteryMaterial != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + (designedMaxCapacity != null ? designedMaxCapacity.hashCode() : 0);
        result = 31 * result + (designedAverageTemperature != null ? designedAverageTemperature.hashCode() : 0);
        result = 31 * result + (designedAverageVoltage != null ? designedAverageVoltage.hashCode() : 0);
        result = 31 * result + (batteryMaterial != null ? batteryMaterial.hashCode() : 0);
        return result;
    }
}
