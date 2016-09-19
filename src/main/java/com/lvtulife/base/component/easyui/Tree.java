package com.lvtulife.base.component.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI tree模型
 *
 * @author 孙宇
 *
 */
public class Tree implements java.io.Serializable {

	private Integer id;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<Tree> children = new ArrayList<Tree>(0);
	private String iconCls;
	private Integer pid;

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Tree() {
	}

	public Tree(Integer id, String text, Object attributes, String iconCls, Integer pid) {
		this.id = id;
		this.text = text;
		this.attributes = attributes;
		this.iconCls = iconCls;
		this.pid = pid;
	}
}
