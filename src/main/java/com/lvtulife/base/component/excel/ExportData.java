package com.lvtulife.base.component.excel;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Taven
 * Date: 13-7-6
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public class ExportData {


    private String sheetName;
    private CellHead cellHead;
    private List<HashMap<String, Object>> dataList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public CellHead getCellHead() {
        return cellHead;
    }

    public void setCellHead(CellHead cellHead) {
        this.cellHead = cellHead;
    }

    public List<HashMap<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<HashMap<String, Object>> dataList) {
        this.dataList = dataList;
    }


}
