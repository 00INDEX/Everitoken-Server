package everitoken.EveriTokenOperation;

public class BatteryDetail {
    private String name;
    private String owner;


    public BatteryDetail(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
