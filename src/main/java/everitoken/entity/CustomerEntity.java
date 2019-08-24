package everitoken.entity;

import javax.persistence.*;

@Entity
@Table(name = "customer", schema = "everitoken", catalog = "")
public class CustomerEntity {
    private Integer customerUid;
    private Integer customerSex;
    private String customerName;
    private String customerIdnumber;
    private String customerPrivateKey;
    private String customerPhone;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "customer_uid")
    public Integer getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(Integer customerUid) {
        this.customerUid = customerUid;
    }



    @Basic
    @Column(name = "customer_sex")
    public Integer getCustomerSex() {
        return customerSex;
    }

    public void setCustomerSex(Integer customerSex) {
        this.customerSex = customerSex;
    }

    @Basic
    @Column(name = "customer_name")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Basic
    @Column(name = "customer_phone")
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Basic
    @Column(name = "customer_idnumber")
    public String getCustomerIdnumber() {
        return customerIdnumber;
    }

    public void setCustomerIdnumber(String customerIdnumber) {
        this.customerIdnumber = customerIdnumber;
    }

    @Basic
    @Column(name = "customer_private_key")
    public String getCustomerPrivateKey() {
        return customerPrivateKey;
    }

    public void setCustomerPrivateKey(String customerPrivateKey) {
        this.customerPrivateKey = customerPrivateKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (customerUid != that.customerUid) return false;
        if (customerPhone != null ? !customerPhone.equals(that.customerPhone) : that.customerPhone != null)
            return false;
        if (customerSex != null ? !customerSex.equals(that.customerSex) : that.customerSex != null) return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (customerIdnumber != null ? !customerIdnumber.equals(that.customerIdnumber) : that.customerIdnumber != null)
            return false;
        if (customerPrivateKey != null ? !customerPrivateKey.equals(that.customerPrivateKey) : that.customerPrivateKey != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerUid;
        result = 31 * result + (customerPhone != null ? customerPhone.hashCode() : 0);
        result = 31 * result + (customerSex != null ? customerSex.hashCode() : 0);
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (customerIdnumber != null ? customerIdnumber.hashCode() : 0);
        result = 31 * result + (customerPrivateKey != null ? customerPrivateKey.hashCode() : 0);
        return result;
    }
}
