package com.light.model;

public class Instruction {
	public final static String SPLITWORD = " ";

	String imei;
	String l1;
	String l2;
	String l3;
	String l4;
	String l5;
	String l6;
	String fan;

	public Instruction() {

	}

	public Instruction(String imei, String l1, String l2, String l3, String l4, String l5, String l6, String fan) {
		this.imei = imei;
		this.l1 = l1;
		this.l2 = l2;
		this.l3 = l3;
		this.l4 = l4;
		this.l5 = l5;
		this.l6 = l6;
		this.fan = fan;
	}

	public String toString() {
		return l1 + SPLITWORD + l2 + SPLITWORD + l3 + SPLITWORD + l4 + SPLITWORD + l5 + SPLITWORD + l6 + SPLITWORD
				+ fan;
	}

	public static String parseInstruction(int gear) {
		if (gear >= 7) {
			throw new IllegalArgumentException();
		}
		String result = "";
		for (int i = 0; i < gear; i++) {
			result += Device.LightStatus.ON;
			result += SPLITWORD;
		}
		for (int i = 0; i < 6 - gear; i++) {
			result += Device.LightStatus.OFF;
			result += SPLITWORD;
		}
		result += "F" + gear;
		return result;
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

	public String getFan() {
		return fan;
	}

	public void setFan(String fan) {
		this.fan = fan;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}