package com.lvtulife.base.component.easyui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EasyUI DataGrid模型
 *
 * @author 孙宇
 *
 */
public class Grid {

	private Long total = 0L;
	private List rows = new ArrayList();
	private Map<String, Object> combo = null;//下拉框的选项集合

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Map<String, Object> getCombo() {
		return combo;
	}

	public void setCombo(Map<String, Object> combo) {
		this.combo = combo;
	}
}
