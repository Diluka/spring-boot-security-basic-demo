/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.entity;

import cn.net.withub.demo.bootsec.hello.common.Constant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Diluka
 */
@Entity
@Table(name = "userinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userinfo.findAll", query = "SELECT u FROM Userinfo u"),
    @NamedQuery(name = "Userinfo.findByCourtNo", query = "SELECT u FROM Userinfo u WHERE u.userinfoPK.courtNo = :courtNo"),
    @NamedQuery(name = "Userinfo.findByUsername", query = "SELECT u FROM Userinfo u WHERE u.userinfoPK.username = :username"),
    @NamedQuery(name = "Userinfo.findByPassword", query = "SELECT u FROM Userinfo u WHERE u.password = :password"),
    @NamedQuery(name = "Userinfo.findByEnable", query = "SELECT u FROM Userinfo u WHERE u.enable = :enable")})
public class Userinfo implements Serializable, UserDetails {

    @JsonIgnore
    @ManyToMany(mappedBy = "userinfoList")
    private List<Roleinfo> roleinfoList;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserinfoPK userinfoPK;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @Column(name = "enable")
    private Boolean enable;
    @JoinColumn(name = "court_no", referencedColumnName = "court_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CourtTest courtTest;

    public Userinfo() {
    }

    public Userinfo(UserinfoPK userinfoPK) {
        this.userinfoPK = userinfoPK;
    }

    public Userinfo(int courtNo, String username) {
        this.userinfoPK = new UserinfoPK(courtNo, username);
    }

    public UserinfoPK getUserinfoPK() {
        return userinfoPK;
    }

    public void setUserinfoPK(UserinfoPK userinfoPK) {
        this.userinfoPK = userinfoPK;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public CourtTest getCourtTest() {
        return courtTest;
    }

    public void setCourtTest(CourtTest courtTest) {
        this.courtTest = courtTest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userinfoPK != null ? userinfoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userinfo)) {
            return false;
        }
        Userinfo other = (Userinfo) object;
        if ((this.userinfoPK == null && other.userinfoPK != null) || (this.userinfoPK != null && !this.userinfoPK.equals(other.userinfoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.net.withub.demo.bootsec.hello.entity.Userinfo[ userinfoPK=" + userinfoPK + " ]";
    }

    @XmlTransient
    public List<Roleinfo> getRoleinfoList() {
        return roleinfoList;
    }

    public void setRoleinfoList(List<Roleinfo> roleinfoList) {
        this.roleinfoList = roleinfoList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoleinfoList();
    }

    @Override
    public String getUsername() {
        return this.userinfoPK.getCourtNo() + Constant.COURT_NO_USERNAME_SEPARATOR + this.userinfoPK.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }

}
