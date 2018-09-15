package com.yj.db.model.reuse;

import java.io.Serializable;
import java.util.List;

import org.apache.lucene.document.AbstractField;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;


public class BasicModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long databaseID;
	private String sidx;
	private String sord;
	private Integer beginPage;
	private Integer endPage;
	private Integer perPage;
	
	private Long total;
	private double totalb;
	
	/*************************************
	 * luncene
	 **************************************/
	
	protected List<AbstractField> luceneField;//lucene字段
	
	public double getTotalb() {
		return totalb;
	}
	public void setTotalb(double totalb) {
		this.totalb = totalb;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<AbstractField> createtLuceneField() {
		return luceneField;
	}
	public void loadValueFromLuncene(Document doc){
		
	}
	public void loadValueFromLuncene(Document doc,List items){
		
	}

	public Query createQuery(){
		return null;
	}
	
	public Sort createSort(){     
		return Sort.INDEXORDER;
	}
	
	
	/******************************
	 * 
	 * setter & getter
	 * 
	 ******************************/
	
	public Integer getPerPage() {
		return perPage;
	}
	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}
	public Integer getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(Integer beginPage) {
		this.beginPage = beginPage;
	}
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}
	public Long getDatabaseID() {
		return databaseID;
	}
	public void setDatabaseID(Long databaseID) {
		this.databaseID = databaseID;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	
}
