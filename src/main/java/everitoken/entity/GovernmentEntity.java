package everitoken.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "government", schema = "everitoken", catalog = "")
public class GovernmentEntity {
    private int governmentUid;
    private String governmentUsername;
    private String governmentPassword;
    private String governmentName;
    private String governmentPrivateKey;
    private String governmentChnCode;

    @Id
    @Column(name = "government_uid")
    public int getGovernmentUid() {
        return governmentUid;
    }

    public void setGovernmentUid(int governmentUid) {
        this.governmentUid = governmentUid;
    }

    @Basic
    @Column(name = "government_username")
    public String getGovernmentUsername() {
        return governmentUsername;
    }

    public void setGovernmentUsername(String governmentUsername) {
        this.governmentUsername = governmentUsername;
    }

    @Basic
    @Column(name = "government_password")
    public String getGovernmentPassword() {
        return governmentPassword;
    }

    public void setGovernmentPassword(String governmentPassword) {
        this.governmentPassword = governmentPassword;
    }

    @Basic
    @Column(name = "government_name")
    public String getGovernmentName() {
        return governmentName;
    }

    public void setGovernmentName(String governmentName) {
        this.governmentName = governmentName;
    }

    @Basic
    @Column(name = "government_private_key")
    public String getGovernmentPrivateKey() {
        return governmentPrivateKey;
    }

    public void setGovernmentPrivateKey(String governmentPrivateKey) {
        this.governmentPrivateKey = governmentPrivateKey;
    }

    @Basic
    @Column(name = "government_CHNCode")
    public String getGovernmentChnCode() {
        return governmentChnCode;
    }

    public void setGovernmentChnCode(String governmentChnCode) {
        this.governmentChnCode = governmentChnCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GovernmentEntity)) return false;
        GovernmentEntity that = (GovernmentEntity) o;
        return getGovernmentUid() == that.getGovernmentUid() &&
                getGovernmentUsername().equals(that.getGovernmentUsername()) &&
                getGovernmentPassword().equals(that.getGovernmentPassword()) &&
                getGovernmentName().equals(that.getGovernmentName()) &&
                getGovernmentPrivateKey().equals(that.getGovernmentPrivateKey()) &&
                getGovernmentChnCode().equals(that.getGovernmentChnCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGovernmentUid(), getGovernmentUsername(), getGovernmentPassword(), getGovernmentName(), getGovernmentPrivateKey(), getGovernmentChnCode());
    }
}
