package com.bns.DataClass;public class Word {	String word;	String mean;	int id;		public Word(String word,String mean)	{		this.word = word;		this.mean = mean;		this.id = 0;	}		public Word(int id,String word,String mean)	{		this.word = word;		this.mean = mean;		this.id = id;	}		public Word()	{		this.word = null;		this.mean = null;		this.id = 0;	}	public String getWord() {		return word;	}	public void setWord(String word) {		this.word = word;	}	public String getMean() {		return mean;	}	public void setMean(String mean) {		this.mean = mean;	}	public int getId() {		return id;	}	public void setId(int id) {		this.id = id;	}}