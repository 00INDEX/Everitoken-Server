package everitoken.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "producer", schema = "everitoken", catalog = "")
public class ProducerEntity {
    private int producerUid;
    private String producerUsername;
    private String producerPassword;
    private String producerEmail;
    private String producerName;
    private String producerPrivateKey;
    private String producerChnCode;
    private int authorized;

    @Id
    @Column(name = "producer_uid")
    public int getProducerUid() {
        return producerUid;
    }

    public void setProducerUid(int ProducerUid) {
        this.producerUid = ProducerUid;
    }

    @Basic
    @Column(name = "producer_username")
    public String getProducerUsername() {
        return producerUsername;
    }

    public void setProducerUsername(String ProducerUsername) {
        this.producerUsername = ProducerUsername;
    }

    @Basic
    @Column(name = "producer_password")
    public String getProducerPassword() {
        return producerPassword;
    }

    public void setProducerPassword(String ProducerPassword) {
        this.producerPassword = ProducerPassword;
    }

    @Basic
    @Column(name = "producer_name")
    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String ProducerName) {
        this.producerName = ProducerName;
    }

    @Basic
    @Column(name = "producer_private_key")
    public String getProducerPrivateKey() {
        return producerPrivateKey;
    }

    public void setProducerPrivateKey(String ProducerPrivateKey) {
        this.producerPrivateKey = ProducerPrivateKey;
    }

    @Basic
    @Column(name = "producer_CHNCode")
    public String getProducerChnCode() {
        return producerChnCode;
    }

    public void setProducerChnCode(String ProducerChnCode) {
        this.producerChnCode = ProducerChnCode;
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
    @Column(name = "authorized")
    public int getAuthorized() {
        return authorized;
    }

    public void setAuthorized(int authorized) {
        this.authorized = authorized;
    }
}
