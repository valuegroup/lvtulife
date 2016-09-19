package com.lvtulife.base.component.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lvtulife.base.utils.BeanUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;

public class XlsDataConvertUtil {
    public ExportData buildExportDataByBean(String sheetName, List<ColumnFiled> columnFileds, List dataList) {
        ExportData exportData = new ExportData();
        // 构造表的列头
        CellHead cellHead = new CellHead();
        cellHead.setColumnFileds(columnFileds);
        // 构造每行的数据
        List<HashMap<String, Object>> rowList = this.buildRowDataListByBean(columnFileds, dataList);
        // 表单名
        exportData.setSheetName(sheetName);
        exportData.setDataList(rowList);
        exportData.setCellHead(cellHead);
        return exportData;
    }

    public ExportData buildExportData(String sheetName, List<ColumnFiled> columnFileds, List dataList) {
        ExportData exportData = new ExportData();
        // 构造表的列头
        CellHead cellHead = new CellHead();
        cellHead.setColumnFileds(columnFileds);
        // 构造每行的数据
        List<HashMap<String, Object>> rowList = this.buildRowDataList(columnFileds, dataList);
        // 表单名
        exportData.setSheetName(sheetName);
        exportData.setDataList(rowList);
        exportData.setCellHead(cellHead);
        return exportData;
    }

    public List<HashMap<String, Object>> buildRowDataListByBean(List<ColumnFiled> columnFileds, List dataList) {
        List<HashMap<String, Object>> rowList = new ArrayList<HashMap<String, Object>>();
        for (Object object : dataList) {
            Object row = object;
            HashMap<String, Object> rowData = new HashMap<String, Object>();
            for (ColumnFiled cFiled : columnFileds) {
                Object _value = BeanUtils.getProperty(row, cFiled.getBeanFiledName());
                if (_value == null) {
                    rowData.put(cFiled.getFiledName(), "");
                } else {
                    rowData.put(cFiled.getFiledName(), _value);
                }
            }
            rowList.add(rowData);
        }
        return rowList;
    }

    public List<HashMap<String, Object>> buildRowDataList(List<ColumnFiled> columnFileds, List dataList) {
        List<HashMap<String, Object>> rowList = new ArrayList<HashMap<String, Object>>();
        for (Object object : dataList) {
            Object[] row = (Object[]) object;
            HashMap<String, Object> rowData = new HashMap<String, Object>();
            for (ColumnFiled cFiled : columnFileds) {
                if (row[cFiled.getCIndex()] == null) {
                    rowData.put(cFiled.getFiledName(), "");
                } else {
                    rowData.put(cFiled.getFiledName(), row[cFiled.getCIndex()]);
                }
            }
            rowList.add(rowData);
        }
        return rowList;
    }

    public List<HashMap<String, Object>> sheetToList(List<ColumnFiled> columnFileds, Sheet sheet) {
        List<HashMap<String, Object>> rowList = new ArrayList<HashMap<String, Object>>();
        int rowNum = sheet.getRows();
        for (int r = 1; r < rowNum; r++) {
            Cell[] cells = sheet.getRow(r);
            if (cells.length != 0) {
                HashMap<String, Object> rowData = new HashMap<String, Object>();
                boolean isEmpty = true;
                for (ColumnFiled cFiled : columnFileds) {
                    if (cells.length <= cFiled.getCIndex()) {
                        rowData.put(cFiled.getFiledName(), "");
                    } else {
                        String cell = cells[cFiled.getCIndex()].getContents();
                        if (!StringUtils.isBlank(cell)) {
                            isEmpty = false;
                        }
                        rowData.put(cFiled.getFiledName(), cell);
                    }
                }

                if (!isEmpty) {
                    rowList.add(rowData);
                }
            }
        }
        return rowList;
    }

    public void listToSheet(ExportData exportData, WritableWorkbook workbook) throws RowsExceededException, WriteException {
        WritableSheet xmlSheet = workbook.createSheet(exportData.getSheetName(), 0);
        // 写表头
        List<ColumnFiled> columnFileds = exportData.getCellHead().getColumnFileds();
        for (int c = 0; c < columnFileds.size(); c++) {
            // 设置列宽
            xmlSheet.setColumnView(c, columnFileds.get(c).getWidth());
            xmlSheet.addCell(new Label(c, 0, columnFileds.get(c).getFiledName()));
        }
        // 写入数据
        int r = 1;
        List<HashMap<String, Object>> dataList = exportData.getDataList();
        for (HashMap<String, Object> rowData : dataList) {
            for (ColumnFiled cFiled : columnFileds) {
                Object dataValue = new Object();
                if (rowData.containsKey(cFiled.getFiledName())) {
                    dataValue = rowData.get(cFiled.getFiledName());
                } else {
                    dataValue = "";
                }
                xmlSheet.addCell(new Label(cFiled.getCIndex(), r, dataValue.toString()));
            }
            r++;
        }
    }

    public int getImportRowsCount(Sheet sheet) {
        return sheet.getRows() > 0 ? sheet.getRows() - 1 : sheet.getRows();// 导入的条数要减去头占的一行
    }

    public String getStringRowData(HashMap<String, Object> rowData, String keyName) {
        return rowData.containsKey(keyName) ? rowData.get(keyName).toString() : "";
    }

    public int getIntRowData(HashMap<String, Object> rowData, String keyName) {
        if (rowData.containsKey(keyName)) {
            String keyValue = rowData.get(keyName).toString();
            return Integer.parseInt(keyValue.trim().length() < 1 ? "0" : keyValue);
        } else {
            return 0;
        }
    }

    public byte getByteRowData(HashMap<String, Object> rowData, String keyName) {
        if (rowData.containsKey(keyName)) {
            String keyValue = rowData.get(keyName).toString();
            return Byte.parseByte(keyValue.trim().length() < 1 ? "0" : keyValue);
        } else {
            return 0;
        }
    }
}
