package everitoken.entity;

import javax.persistence.*;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
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
        if (o == null || getClass() != o.getClass()) return false;

        GovernmentEntity that = (GovernmentEntity) o;

        if (governmentUid != that.governmentUid) return false;
        if (governmentUsername != null ? !governmentUsername.equals(that.governmentUsername) : that.governmentUsername != null)
            return false;
        if (governmentPassword != null ? !governmentPassword.equals(that.governmentPassword) : that.governmentPassword != null)
            return false;
        if (governmentName != null ? !governmentName.equals(that.governmentName) : that.governmentName != null)
            return false;
        if (governmentPrivateKey != null ? !governmentPrivateKey.equals(that.governmentPrivateKey) : that.governmentPrivateKey != null)
            return false;
        if (governmentChnCode != null ? !governmentChnCode.equals(that.governmentChnCode) : that.governmentChnCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = governmentUid;
        result = 31 * result + (governmentUsername != null ? governmentUsername.hashCode() : 0);
        result = 31 * result + (governmentPassword != null ? governmentPassword.hashCode() : 0);
        result = 31 * result + (governmentName != null ? governmentName.hashCode() : 0);
        result = 31 * result + (governmentPrivateKey != null ? governmentPrivateKey.hashCode() : 0);
        result = 31 * result + (governmentChnCode != null ? governmentChnCode.hashCode() : 0);
        return result;
    }
}
