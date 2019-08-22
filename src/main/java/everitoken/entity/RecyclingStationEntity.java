package everitoken.entity;

import javax.persistence.*;

@Entity
@Table(name = "recycling_station", schema = "everitoken", catalog = "")
public class RecyclingStationEntity {
    private String rsName;
    private String rsPrivateKey;
    private int rsUid;

    @Basic
    @Column(name = "rs_name")
    public String getRsName() {
        return rsName;
    }

    public void setRsName(String rsName) {
        this.rsName = rsName;
    }

    @Basic
    @Column(name = "rs_private_key")
    public String getRsPrivateKey() {
        return rsPrivateKey;
    }

    public void setRsPrivateKey(String rsPrivateKey) {
        this.rsPrivateKey = rsPrivateKey;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "rs_uid")
    public int getRsUid() {
        return rsUid;
    }

    public void setRsUid(int rsUid) {
        this.rsUid = rsUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecyclingStationEntity that = (RecyclingStationEntity) o;

        if (rsUid != that.rsUid) return false;
        if (rsName != null ? !rsName.equals(that.rsName) : that.rsName != null) return false;
        if (rsPrivateKey != null ? !rsPrivateKey.equals(that.rsPrivateKey) : that.rsPrivateKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rsName != null ? rsName.hashCode() : 0;
        result = 31 * result + (rsPrivateKey != null ? rsPrivateKey.hashCode() : 0);
        result = 31 * result + rsUid;
        return result;
    }
}
