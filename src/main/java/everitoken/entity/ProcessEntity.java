package everitoken.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "process", schema = "everitoken", catalog = "")
public class ProcessEntity {
    private Integer uid;
    private Integer applicantUid;
    private Integer processorUid;
    private Timestamp processTime;
    private String processReason;

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
    @Column(name = "processor_uid")
    public Integer getProcessorUid() {
        return processorUid;
    }

    public void setProcessorUid(Integer processorUid) {
        this.processorUid = processorUid;
    }

    @Basic
    @Column(name = "process_time")
    public Timestamp getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Timestamp processTime) {
        this.processTime = processTime;
    }

    @Basic
    @Column(name = "process_reason")
    public String getProcessReason() {
        return processReason;
    }

    public void setProcessReason(String processReason) {
        this.processReason = processReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessEntity that = (ProcessEntity) o;

        if (uid != that.uid) return false;
        if (applicantUid != null ? !applicantUid.equals(that.applicantUid) : that.applicantUid != null) return false;
        if (processorUid != null ? !processorUid.equals(that.processorUid) : that.processorUid != null) return false;
        if (processTime != null ? !processTime.equals(that.processTime) : that.processTime != null) return false;
        if (processReason != null ? !processReason.equals(that.processReason) : that.processReason != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + (applicantUid != null ? applicantUid.hashCode() : 0);
        result = 31 * result + (processorUid != null ? processorUid.hashCode() : 0);
        result = 31 * result + (processTime != null ? processTime.hashCode() : 0);
        result = 31 * result + (processReason != null ? processReason.hashCode() : 0);
        return result;
    }
}
