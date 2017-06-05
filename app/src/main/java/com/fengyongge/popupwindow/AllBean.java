package com.fengyongge.popupwindow;

import java.io.Serializable;

public class AllBean implements Serializable {
	

	private Boolean is_check;
	private String name;
	private String id;


	public Boolean getIs_check() {
		return is_check;
	}

	public void setIs_check(Boolean is_check) {
		this.is_check = is_check;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
