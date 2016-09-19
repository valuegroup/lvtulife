package com.lvtulife.base.component.easyui;

public class Combobox implements java.io.Serializable {
	private static final long serialVersionUID = -1045839100279171680L;
	private Integer id;
	private String text;
	private String desc;
	private boolean selected = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
