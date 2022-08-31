package com.light.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plan")
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String pname;

	private String expression;

	private String expressioff;

	private String startTime;

	private String endTime;

	private String instruction;

	private Date createTime = new Date();

	public String getInsByIndex(int index) {
		String[] insArray = instruction.split(" ");
		return insArray[index];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getExpressioff() {
		return expressioff;
	}

	public void setExpressioff(String expressioff) {
		this.expressioff = expressioff;
	}

}
