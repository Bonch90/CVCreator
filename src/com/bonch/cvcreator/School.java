package com.bonch.cvcreator;

import java.io.Serializable;

public class School implements Serializable,Comparable<Object>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String startDate;
	public String endDate;
	public String nome;
	public String materie;
	public String qualifica;
	@Override
	public String toString(){
		return startDate+" - "+endDate;
	}
	
	@Override
	public int compareTo(Object another) {
		School school=(School)another;
		int start=this.startDate.compareTo(school.startDate);
		int end=this.endDate.compareTo(school.endDate);
		if(start<0)
			return 1;
		if(start==0)
			return end;
		return -1;
	}
}
