package com.bonch.cvcreator;

import java.io.Serializable;

public class Language implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int name;
	public int lettura;
	public int scrittura;
	public int orale;
	@Override
	public String toString(){
		return Abilities.dataAdapter.getItem(name).toString();
	}
	
}
