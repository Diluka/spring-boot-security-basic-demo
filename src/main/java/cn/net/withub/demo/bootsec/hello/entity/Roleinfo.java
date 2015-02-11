/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Diluka
 */
@Entity
@Table(name = "roleinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roleinfo.findAll", query = "SELECT r FROM Roleinfo r"),
    @NamedQuery(name = "Roleinfo.findById", query = "SELECT r FROM Roleinfo r WHERE r.id = :id"),
    @NamedQuery(name = "Roleinfo.findByName", query = "SELECT r FROM Roleinfo r WHERE r.name = :name"),
    @NamedQuery(name = "Roleinfo.findByEnable", query = "SELECT r FROM Roleinfo r WHERE r.enable = :enable")})
public class Roleinfo implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Column(name = "enable")
    private Boolean enable;
    @JsonIgnore
    @JoinTable(name = "user_role", joinColumns = {
        @JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "username", referencedColumnName = "username"),
        @JoinColumn(name = "court_no", referencedColumnName = "court_no")})
    @ManyToMany
    private List<Userinfo> userinfoList;
    @JsonIgnore
    @ManyToMany(mappedBy = "roleinfoList")
    private List<Resource> resourceList;

    public Roleinfo() {
    }

    public Roleinfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @XmlTransient
    public List<Userinfo> getUserinfoList() {
        return userinfoList;
    }

    public void setUserinfoList(List<Userinfo> userinfoList) {
        this.userinfoList = userinfoList;
    }

    @XmlTransient
    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roleinfo)) {
            return false;
        }
        Roleinfo other = (Roleinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.net.withub.demo.bootsec.hello.entity.Roleinfo[ id=" + id + " ]";
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }

}
