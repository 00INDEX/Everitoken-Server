package everitoken.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "application", schema = "everitoken", catalog = "")
public class ApplicationEntity {
    private Integer uid;
    private Integer applicantUid;
    private Timestamp applicationTime;
    private String applicationDocuments;

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
    @Column(name = "applicant_uid")
    public Integer getApplicantUid() {
        return applicantUid;
    }

    public void setApplicantUid(Integer applicantUid) {
        this.applicantUid = applicantUid;
    }

    @Basic
    @Column(name = "application_time")
    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
        this.applicationTime = applicationTime;
    }

    @Basic
    @Column(name = "application_documents")
    public String getApplicationDocuments() {
        return applicationDocuments;
    }

    public void setApplicationDocuments(String applicationDocuments) {
        this.applicationDocuments = applicationDocuments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationEntity that = (ApplicationEntity) o;

        if (uid != that.uid) return false;
        if (applicantUid != null ? !applicantUid.equals(that.applicantUid) : that.applicantUid != null) return false;
        if (applicationTime != null ? !applicationTime.equals(that.applicationTime) : that.applicationTime != null)
            return false;
        if (applicationDocuments != null ? !applicationDocuments.equals(that.applicationDocuments) : that.applicationDocuments != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + (applicantUid != null ? applicantUid.hashCode() : 0);
        result = 31 * result + (applicationTime != null ? applicationTime.hashCode() : 0);
        result = 31 * result + (applicationDocuments != null ? applicationDocuments.hashCode() : 0);
        return result;
    }
}
