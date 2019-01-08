package at.qe.sepm.skeleton.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.domain.Persistable;

/**
 * Entity representing users.
 */
@Entity
public class User implements Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 20)
    private String username;

    @ManyToOne(optional = false)
    private User createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @ManyToOne(optional = true)
    private User updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(length = 100)

    private String cNumber;

    private String password;

    private String firstName;
    private String lastName;
    private String email;

    boolean enabled;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EquipmentGroup> equipmentGroups;

    @Fetch(FetchMode.SELECT)
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "User_UserRole")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    public String getcNumber()
    {
        return this.cNumber;
    }

    public void setcNumber(String cNumber)
    {
        this.cNumber = cNumber;
    }

    public void addEquipmentGroup(EquipmentGroup equipmentGroup){
        equipmentGroup.setUser(this);
        equipmentGroups.add(equipmentGroup);
    }

    public void removeEquipmentGroup(EquipmentGroup equipmentGroup){
        equipmentGroups.remove(equipmentGroup);
        if(equipmentGroup != null){
            equipmentGroup.setUser(null);
        }
    }

    public List<EquipmentGroup> getEquipmentGroups() {
        return equipmentGroups;
    }

    public void setEquipmentGroups(List<EquipmentGroup> equipmentGroups) {
        this.equipmentGroups = equipmentGroups;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public String getHighestPermission() {
        if (roles.contains(UserRole.ADMIN)) {
            return "ADMIN";
        } else if (roles.contains(UserRole.EMPLOYEE)) {
            return "EMPLOYEE";
        } else {
            return "STUDENT";
        }
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
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

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ id=" + username + " ]";
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }

}
