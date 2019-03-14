package com.evgen.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="item")
public class Item {
	
	@Id
	@GeneratedValue
    @Column(name="id")
    private Long id;
	
	@Column(name="title")
	private String title ;
	
	@Column(name="price")
	private BigDecimal price ;
	
	@Column(name="url")
	private String url ;
	
	public Item(Long id, String title, BigDecimal price, String url) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.url = url;
	}
	public Item(String title, BigDecimal price, String url) {
		super();
		this.title = title;
		this.price = price;
		this.url = url;
	}
	public Item() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "Item [title=" + title + ", price=" + price + ", url=" + url + "]";
	}
	
}
