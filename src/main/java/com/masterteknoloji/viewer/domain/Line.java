package com.masterteknoloji.viewer.domain;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.masterteknoloji.viewer.domain.dto.VideoRecordQueryVM;

public class Line {
	
	Long id ;
	String name;
	Point start;
	Point end;
	Point projectedStart = new Point();
	Point projectedEnd = new Point();
	Rectangle reportRectangle = new Rectangle();
	
	
	Long count = 0l;
	
	Color color = Color.yellow;
	Color orijinalColor= Color.yellow;
	
	@JsonIgnore
	Camera camera;
	
	@JsonIgnore
	List<VideoRecordQueryVM> data = new ArrayList<VideoRecordQueryVM>();
	
	@JsonIgnore
	Map<String,Long> typeDetails = new HashMap<String,Long>();
	
	@JsonIgnore
	Queue<VideoRecordQueryVM> lastDatas = new LinkedList<VideoRecordQueryVM>();
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Point getStart() {
		return start;
	}
	public void setStart(Point start) {
		this.start = start;
	}
	public Point getEnd() {
		return end;
	}
	public void setEnd(Point end) {
		this.end = end;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public List<VideoRecordQueryVM> getData() {
		return data;
	}
	public void setData(List<VideoRecordQueryVM> data) {
		this.data = data;
	}
	public Point getProjectedStart() {
		return projectedStart;
	}
	public void setProjectedStart(Point projectedStart) {
		this.projectedStart = projectedStart;
	}
	public Point getProjectedEnd() {
		return projectedEnd;
	}
	public void setProjectedEnd(Point projectedEnd) {
		this.projectedEnd = projectedEnd;
	}
	public Map<String, Long> getTypeDetails() {
		return typeDetails;
	}
	public void setTypeDetails(Map<String, Long> typeDetails) {
		this.typeDetails = typeDetails;
	}
	public Queue<VideoRecordQueryVM> getLastDatas() {
		return lastDatas;
	}
	public void setLastDatas(Queue<VideoRecordQueryVM> lastDatas) {
		this.lastDatas = lastDatas;
	}
	public Rectangle getReportRectangle() {
		return reportRectangle;
	}
	public void setReportRectangle(Rectangle reportRectangle) {
		this.reportRectangle = reportRectangle;
	}
	public Color getOrijinalColor() {
		return orijinalColor;
	}
	public void setOrijinalColor(Color orijinalColor) {
		this.orijinalColor = orijinalColor;
	}

	
	
}
