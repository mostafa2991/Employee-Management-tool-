package com.hegazy.dao;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Employee {

	private int id;
	private String code;
	private String name;
	private String address;
	private String email;

	public Employee() {
	}

	public Employee(int id, String code, String name, String address, String email) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.address = address;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", code=" + code + ", name=" + name + ", address=" + address + ", email=" + email
				+ "]";
	}

	

}
