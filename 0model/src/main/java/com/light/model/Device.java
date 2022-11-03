package com.light.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String dname;

	private String imei;

	private Integer groupId;

	private String fan = "OFF";// on off bad

	private String l1 = "OFF";// on off bad
	
	private int l1usedTime;

	private String l2 = "OFF";
	
	private int l2usedTime;

	private String l3 = "OFF";
	
	private int l3usedTime;

	private String l4 = "OFF";
	
	private int l4usedTime;

	private String l5 = "OFF";
	
	private int l5usedTime;

	private String l6 = "OFF";
	
	private int l6usedTime;

	private String l7 = "OFF";

	private String l8 = "OFF";

	private Double temperature;// 温度

	private Double longitude;// 经度

	private Double latitude;// 纬度
	
	private Double rpm;

	private String status = "OFFLINE";// 状态  OFFLINE ONLINE

	private int expire;// 使用时间

	private Date createTime = new Date();

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public Double getRpm() {
		return rpm;
	}

	public void setRpm(Double rpm) {
		this.rpm = rpm;
	}

	public int getL1usedTime() {
		return l1usedTime;
	}

	public void setL1usedTime(int l1usedTime) {
		this.l1usedTime = l1usedTime;
	}

	public int getL2usedTime() {
		return l2usedTime;
	}

	public void setL2usedTime(int l2usedTime) {
		this.l2usedTime = l2usedTime;
	}

	public int getL3usedTime() {
		return l3usedTime;
	}

	public void setL3usedTime(int l3usedTime) {
		this.l3usedTime = l3usedTime;
	}

	public int getL4usedTime() {
		return l4usedTime;
	}

	public void setL4usedTime(int l4usedTime) {
		this.l4usedTime = l4usedTime;
	}

	public int getL5usedTime() {
		return l5usedTime;
	}

	public void setL5usedTime(int l5usedTime) {
		this.l5usedTime = l5usedTime;
	}

	public int getL6usedTime() {
		return l6usedTime;
	}

	public void setL6usedTime(int l6usedTime) {
		this.l6usedTime = l6usedTime;
	}
}
