package everitoken.entity;

import javax.persistence.*;

@Entity
@Table(name = "producer", schema = "everitoken", catalog = "")
public class ProducerEntity {
    private int producerUid;
    private String producerEmail;
    private String producerName;
    private String producerPrivateKey;
    private Byte authorized;
    private String producerChnCode;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "producer_uid")
    public int getProducerUid() {
        return producerUid;
    }

    public void setProducerUid(int producerUid) {
        this.producerUid = producerUid;
    }

    @Basic
    @Column(name = "producer_email")
    public String getProducerEmail() {
        return producerEmail;
    }

    public void setProducerEmail(String producerEmail) {
        this.producerEmail = producerEmail;
    }

    @Basic
    @Column(name = "producer_name")
    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    @Basic
    @Column(name = "producer_private_key")
    public String getProducerPrivateKey() {
        return producerPrivateKey;
    }

    public void setProducerPrivateKey(String producerPrivateKey) {
        this.producerPrivateKey = producerPrivateKey;
    }

    @Basic
    @Column(name = "authorized")
    public Byte getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Byte authorized) {
        this.authorized = authorized;
    }

    @Basic
    @Column(name = "producer_CHNCode")
    public String getProducerChnCode() {
        return producerChnCode;
    }

    public void setProducerChnCode(String producerChnCode) {
        this.producerChnCode = producerChnCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProducerEntity that = (ProducerEntity) o;

        if (producerUid != that.producerUid) return false;
        if (producerEmail != null ? !producerEmail.equals(that.producerEmail) : that.producerEmail != null)
            return false;
        if (producerName != null ? !producerName.equals(that.producerName) : that.producerName != null) return false;
        if (producerPrivateKey != null ? !producerPrivateKey.equals(that.producerPrivateKey) : that.producerPrivateKey != null)
            return false;
        if (authorized != null ? !authorized.equals(that.authorized) : that.authorized != null) return false;
        if (producerChnCode != null ? !producerChnCode.equals(that.producerChnCode) : that.producerChnCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = producerUid;
        result = 31 * result + (producerEmail != null ? producerEmail.hashCode() : 0);
        result = 31 * result + (producerName != null ? producerName.hashCode() : 0);
        result = 31 * result + (producerPrivateKey != null ? producerPrivateKey.hashCode() : 0);
        result = 31 * result + (authorized != null ? authorized.hashCode() : 0);
        result = 31 * result + (producerChnCode != null ? producerChnCode.hashCode() : 0);
        return result;
    }
}
