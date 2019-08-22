package com.erhan.springbootrestcrud.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class PageForClient<T> {

	private int number;
	
	private int size;
	
	private Sort sort;
	
	private int totalPage;
	
	private Long totalElements;
	
	private List<T> content;
	
	
	public void setFields(Page<? extends Object> page, List<T> list) {
		this.number = page.getNumber();
		this.size = page.getSize();
		this.sort = page.getSort();
		this.totalPage = page.getTotalPages();
		this.totalElements = page.getTotalElements();
		this.content = list;	
	}
}
