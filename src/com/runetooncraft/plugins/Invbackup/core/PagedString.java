package com.runetooncraft.plugins.Invbackup.core;

import java.util.ArrayList;
import java.util.HashMap;

public class PagedString {
	private ArrayList<String> Strings = new ArrayList<String>();
	private int AmountPerPage = 0;
	private int Pages = 0;
	private HashMap<String,ArrayList<String>> PageStringList = new HashMap<String,ArrayList<String>>();
	
	public PagedString(ArrayList<String> Strings, int AmountPerPage) {
		this.Strings = Strings;
		this.AmountPerPage = AmountPerPage;
		SetupHashMap();
	}
	
	public int GetAmountPerPage() {
		return AmountPerPage;
	}
	
	public ArrayList<String> GetStrings() {
		return Strings;
	}
	
	private void SetupHashMap() {
		
		int MaxPages = (int) Strings.size() / AmountPerPage;
		int i = 0;
		ArrayList<String> TempStrings = new ArrayList<String>();
		for(String s: Strings) {
			i++;
			TempStrings.add(s);
			if(AmountPerPage == i) {
				Pages++;
				TempStrings.add("&6Page " + Pages + "/" + MaxPages + "&b  Use /ib page <PageNumber>"); //Page 1/5
				PageStringList.put("Page" + Pages, new ArrayList<String>());
				PageStringList.get("Page" + Pages).addAll(TempStrings);
				TempStrings.clear();
				i = 0;
			}
	
		}
	}
	
	public ArrayList<String> GetPage(int Page) {
		if(PageStringList.containsKey("Page" + Page)) {
			return PageStringList.get("Page" + Page);
		}else{
			ArrayList<String> NoPage = new ArrayList<String>();
			NoPage.add("&cPage " + Page + " not found.");
			return NoPage;
		}
	}
	public ArrayList<String> GetPage(String page) {
		if(page.contains("Page")) {
			return PageStringList.get(page);
		}else{
			int PageNumber = Integer.parseInt(page);
			return PageStringList.get("Page" + PageNumber);
		}
	}
	
	public boolean ContaingsPage(String Page) {
		if(Page.contains("Page")) {
			return PageStringList.containsKey(Page);
		}else{
			int PageNumber = Integer.parseInt(Page);
			return PageStringList.containsKey("Page" + PageNumber);
		}
	}
	
	public ArrayList<String> GetFirstPage() {
		if(Pages == 0) {
			return Strings;
		}else if(Pages >=1 ) {
			return PageStringList.get("Page" + 1);
		}else{
			return null;
		}
	}
	
	public int GetPages() {
		return Pages;
	}
}
