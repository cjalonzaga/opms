package com.opms.db.entities;

import com.opms.db.BaseEntity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users_data")
public class UserData extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@OneToOne(mappedBy = "userData" , cascade = CascadeType.ALL)
    private Teacher teacher;
	
	@OneToOne(mappedBy = "userData" , cascade = CascadeType.ALL)
    private Student student;
	
	@OneToOne(mappedBy = "userData" , cascade = CascadeType.ALL)
    private SuperAdmin superAdmin;
	
	@Basic
    @Column(
        name = "city",
        nullable = true,
        updatable = true
    )
	private String city;
	
	@Basic
    @Column(
        name = "zipCode",
        nullable = true,
        updatable = true
    )
	private String zipCode;
	
	@Basic
    @Column(
        name = "province",
        nullable = true,
        updatable = true
    )
	private String province;

	@Basic
    @Column(
        name = "country",
        nullable = true,
        updatable = true
    )
	private String country;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
