package com.blueice.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 用于在“订单列表”中显示的Bean.
 */
public class OrderListForm implements Serializable {

	private String id;
	private double money;
	private String receiverinfo;
	private int paystate;
	private Timestamp ordertime;
	private String userName;	
	private Map<Product, Integer> prodMap;
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getReceiverinfo() {
		return receiverinfo;
	}
	public void setReceiverinfo(String receiverinfo) {
		this.receiverinfo = receiverinfo;
	}
	public int getPaystate() {
		return paystate;
	}
	public void setPaystate(int paystate) {
		this.paystate = paystate;
	}
	public Timestamp getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Timestamp ordertime) {
		this.ordertime = ordertime;
	}
	
	public Map<Product, Integer> getProdMap() {
		return prodMap;
	}
	public void setProdMap(Map<Product, Integer> prodMap) {
		this.prodMap = prodMap;
	}
	
	
	
	
}
