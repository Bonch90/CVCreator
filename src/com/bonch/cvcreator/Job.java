package com.bonch.cvcreator;

import java.io.Serializable;

public class Job implements Serializable, Comparable<Object>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String startDate;
	public String endDate;
	public String nome;
	public String tipo;
	public String mansione;
	public String impiego;
	@Override
	public String toString(){
		return startDate+" - "+endDate;
	}
	@Override
	public int compareTo(Object another) {
		Job job=(Job)another;
		int start=this.startDate.compareTo(job.startDate);
		int end=this.endDate.compareTo(job.endDate);
		if(start<0)
			return 1;
		if(start==0)
			return end;
		return -1;
	}


}
