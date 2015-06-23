package com.divx.service.domain.manager;

import java.util.Comparator;

public class ComparatorPhotoUrl implements Comparator<String>
{
	@Override
	public int compare(String o1, String o2) {
		String url1 = o1;
		String url2 = o2;
		
		String num1 = url1.substring(url1.lastIndexOf('/') + 2).replaceAll(".jpg", "").replaceAll("_", "").trim();
		String num2 = url2.substring(url1.lastIndexOf('/') + 2).replaceAll(".jpg", "").replaceAll("_", "").trim();
		
		return Integer.compare(Integer.parseInt(num1), Integer.parseInt(num2));
	}		
}
