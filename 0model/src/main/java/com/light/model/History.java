package com.light.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "history")
public class History {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String imei;
	
	private String fan;
	
	private String l1;
	
	private String l2;
	
	private String l3;
	
	private String l4;
	
	private String l5;
	
	private String l6;
	
	private String l7;
	
	private String l8;
	
	private Double temperature;//温度
	
	private Double longitude;//经度
	
	private Double latitude;//纬度
	
	private Date date;
	
	private Date dateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFan() {
		return fan;
	}

	public void setFan(String fan) {
		this.fan = fan;
	}

	public String getL1() {
		return l1;
	}

	public void setL1(String l1) {
		this.l1 = l1;
	}

	public String getL2() {
		return l2;
	}

	public void setL2(String l2) {
		this.l2 = l2;
	}

	public String getL3() {
		return l3;
	}

	public void setL3(String l3) {
		this.l3 = l3;
	}

	public String getL4() {
		return l4;
	}

	public void setL4(String l4) {
		this.l4 = l4;
	}

	public String getL5() {
		return l5;
	}

	public void setL5(String l5) {
		this.l5 = l5;
	}

	public String getL6() {
		return l6;
	}

	public void setL6(String l6) {
		this.l6 = l6;
	}

	public String getL7() {
		return l7;
	}

	public void setL7(String l7) {
		this.l7 = l7;
	}

	public String getL8() {
		return l8;
	}

	public void setL8(String l8) {
		this.l8 = l8;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

}
