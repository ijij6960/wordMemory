package com.bns.DataClass;import java.util.*;public class DateDB {	String word;	int count;	String match;	public String getWord() {		return word;	}	public void setWord(String word) {		this.word = word;	}	public int getCount() {		return count;	}	public void setCount(int count) {		this.count = count;	}	public String getMatch() {		return match;	}	public void setMatch(String match) {		this.match = match;	}		public DateDB() {		super();	}		public DateDB(String word, int count, String match) {		super();		this.word = word;		this.count = count;		this.match = match;	}}