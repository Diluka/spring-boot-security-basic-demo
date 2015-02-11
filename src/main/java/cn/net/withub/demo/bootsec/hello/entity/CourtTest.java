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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diluka
 */
@Entity
@Table(name = "court_test")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourtTest.findAll", query = "SELECT c FROM CourtTest c"),
    @NamedQuery(name = "CourtTest.findByCourtNo", query = "SELECT c FROM CourtTest c WHERE c.courtNo = :courtNo"),
    @NamedQuery(name = "CourtTest.findByCourtName", query = "SELECT c FROM CourtTest c WHERE c.courtName = :courtName")})
public class CourtTest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "court_no")
    private Integer courtNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "court_name")
    private String courtName;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courtTest")
    private List<Userinfo> userinfoList;

    public CourtTest() {
    }

    public CourtTest(Integer courtNo) {
        this.courtNo = courtNo;
    }

    public CourtTest(Integer courtNo, String courtName) {
        this.courtNo = courtNo;
        this.courtName = courtName;
    }

    public Integer getCourtNo() {
        return courtNo;
    }

    public void setCourtNo(Integer courtNo) {
        this.courtNo = courtNo;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    @XmlTransient
    public List<Userinfo> getUserinfoList() {
        return userinfoList;
    }

    public void setUserinfoList(List<Userinfo> userinfoList) {
        this.userinfoList = userinfoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courtNo != null ? courtNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourtTest)) {
            return false;
        }
        CourtTest other = (CourtTest) object;
        if ((this.courtNo == null && other.courtNo != null) || (this.courtNo != null && !this.courtNo.equals(other.courtNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.net.withub.demo.bootsec.hello.entity.CourtTest[ courtNo=" + courtNo + " ]";
    }

}
