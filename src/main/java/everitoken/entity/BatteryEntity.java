package everitoken.entity;

import javax.persistence.*;

@Entity
@Table(name = "battery", schema = "everitoken", catalog = "")
public class BatteryEntity {
    private Integer uid;
    private String batteryCapacity;
    private Integer batteryType;
    private String batteryMaxVoltage;
    private String batteryAverageTemperature;
    private Integer batteryChgCycles;
    private String batteryName;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "battery_capacity")
    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Basic
    @Column(name = "battery_type")
    public Integer getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(Integer batteryType) {
        this.batteryType = batteryType;
    }

    @Basic
    @Column(name = "battery_name")
    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    @Basic
    @Column(name = "battery_max_voltage")
    public String getBatteryMaxVoltage() {
        return batteryMaxVoltage;
    }

    public void setBatteryMaxVoltage(String batteryMaxVoltage) {
        this.batteryMaxVoltage = batteryMaxVoltage;
    }

    @Basic
    @Column(name = "battery_average_temperature")
    public String getBatteryAverageTemperature() {
        return batteryAverageTemperature;
    }

    public void setBatteryAverageTemperature(String batteryAverageTemperature) {
        this.batteryAverageTemperature = batteryAverageTemperature;
    }

    @Basic
    @Column(name = "battery_CHG_cycles")
    public Integer getBatteryChgCycles() {
        return batteryChgCycles;
    }

    public void setBatteryChgCycles(Integer batteryChgCycles) {
        this.batteryChgCycles = batteryChgCycles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatteryEntity that = (BatteryEntity) o;

        if (uid != that.uid) return false;
        if (batteryCapacity != null ? !batteryCapacity.equals(that.batteryCapacity) : that.batteryCapacity != null)
            return false;
        if (batteryType != null ? !batteryType.equals(that.batteryType) : that.batteryType != null) return false;
        if (batteryMaxVoltage != null ? !batteryMaxVoltage.equals(that.batteryMaxVoltage) : that.batteryMaxVoltage != null)
            return false;
        if (batteryAverageTemperature != null ? !batteryAverageTemperature.equals(that.batteryAverageTemperature) : that.batteryAverageTemperature != null)
            return false;
        if (batteryChgCycles != null ? !batteryChgCycles.equals(that.batteryChgCycles) : that.batteryChgCycles != null)
            return false;
        if (batteryName != null ? !batteryName.equals(that.batteryName) : that.batteryName != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + (batteryCapacity != null ? batteryCapacity.hashCode() : 0);
        result = 31 * result + (batteryType != null ? batteryType.hashCode() : 0);
        result = 31 * result + (batteryMaxVoltage != null ? batteryMaxVoltage.hashCode() : 0);
        result = 31 * result + (batteryAverageTemperature != null ? batteryAverageTemperature.hashCode() : 0);
        result = 31 * result + (batteryChgCycles != null ? batteryChgCycles.hashCode() : 0);
        result = 31 * result + (batteryName != null ? batteryName.hashCode() : 0);
        return result;
    }
}
