package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.services.StorageService;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import java.io.*;
import java.nio.file.Path;
import java.util.Date;

/**
 * Enity for equipment manuals
 */
@Entity
public class EquipmentManual implements Persistable<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String type;

    @Column
    private String desc;

    @ManyToOne(optional = false)
    private User createUser;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne
    private Equipment equipment;

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }
}
