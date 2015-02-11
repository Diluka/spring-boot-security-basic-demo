/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Diluka
 */
@Embeddable
public class UserinfoPK implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@NotNull
	@Column(name = "court_no")
	private int courtNo;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "username")
	private String username;

	public UserinfoPK() {
	}

	public UserinfoPK(int courtNo, String username) {
		this.courtNo = courtNo;
		this.username = username;
	}

	public int getCourtNo() {
		return courtNo;
	}

	public void setCourtNo(int courtNo) {
		this.courtNo = courtNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += courtNo;
		hash += (username != null ? username.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof UserinfoPK)) {
			return false;
		}
		UserinfoPK other = (UserinfoPK) object;
		if (this.courtNo != other.courtNo) {
			return false;
		}
		if ((this.username == null && other.username != null)
				|| (this.username != null && !this.username
						.equals(other.username))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "cn.net.withub.demo.bootsec.hello.entity.UserinfoPK[ courtNo="
				+ courtNo + ", username=" + username + " ]";
	}

}
