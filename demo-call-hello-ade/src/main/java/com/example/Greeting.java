package com.example;

import java.beans.Transient;

public class Greeting {
	public String name;
	public String salutation;
	
	public Greeting(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	@Transient
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.salutation + " " + this.name;
	}
}