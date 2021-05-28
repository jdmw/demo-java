package com.fourinone.pattern.warehouse;
public interface ObjectBean extends java.io.Serializable{
	public Object toObject();
	public String getName();
	public String getDomain();
	public String getNode();
}