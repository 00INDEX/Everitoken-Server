package everitoken.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "recyling_station", schema = "everitoken", catalog = "")
public class RecyclingStationEntity {
    private int rsUid;
    private String rsUsername;
    private String rsPassword;
    private String rsName;
    private String rsPrivateKey;

    @Id
    @Column(name = "rs_uid")
    public int getRsUid() {
        return rsUid;
    }

    public void setRsUid(int rsUid) {
        this.rsUid = rsUid;
    }

    @Basic
    @Column(name = "rs_username")
    public String getRsUsername() {
        return rsUsername;
    }

    public void setRsUsername(String rsUsername) {
        this.rsUsername = rsUsername;
    }

    @Basic
    @Column(name = "rs_password")
    public String getRsPassword() {
        return rsPassword;
    }

    public void setRsPassword(String rsPassword) {
        this.rsPassword = rsPassword;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecyclingStationEntity)) return false;
        RecyclingStationEntity that = (RecyclingStationEntity) o;
        return getRsUid() == that.getRsUid() &&
                getRsUsername().equals(that.getRsUsername()) &&
                getRsPassword().equals(that.getRsPassword()) &&
                getRsName().equals(that.getRsName()) &&
                getRsPrivateKey().equals(that.getRsPrivateKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRsUid(), getRsUsername(), getRsPassword(), getRsName(), getRsPrivateKey());
    }
}
