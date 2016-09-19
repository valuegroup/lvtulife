package com.lvtulife.system.component.onekeyBuild;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvQuery {
    private static Logger logger = LoggerFactory.getLogger(EnvQuery.class);
    private Vector[] rs;
    private int columns = 0;
    private int rows = 0;
    private int currentRow = -1;
    private Vector fieldName = null;
    private Vector fieldType = null;
    private int recCount = 0;

    protected int select(String strSQL, int pageIndex, int nItemsperPage, int type) {
        this.recCount = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet myResult = null;

        if (logger.isDebugEnabled()) {
            logger.debug("执行查询的语句为：" + strSQL);
        }

        try {
            conn = DB.getConn();
        } catch (SQLException e) {
            logger.error("数据库连接出现异常：" + e.getMessage());
            System.err.println("DataBase Error:" + e.getMessage());
            return this.recCount;
        }

        try {
            if (type == 0) {
                stmt = conn.createStatement(1004, 1007);
                myResult = stmt.executeQuery(strSQL);
            } else if (type == 1) {
                stmt = (CallableStatement) conn.prepareCall(strSQL);
                myResult = ((CallableStatement) stmt).executeQuery();
            }
        } catch (SQLException ex) {
            logger.error("数据查询出现异常：" + ex.getMessage());
            logger.error("错误的查询语句是：" + strSQL);
            System.err.println("Process Error:" + ex.getMessage());

            return this.recCount;
        }

        this.currentRow = 0;
        this.rows = 0;
        this.columns = 0;
        ResultSetMetaData rsMetaData;
        try {
            rsMetaData = myResult.getMetaData();
            this.columns = rsMetaData.getColumnCount();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return this.recCount;
        }

        this.rs = new Vector[this.columns];
        try {
            for (int i = 0; i < this.columns; ++i) {
                if (i == 0) {
                    this.fieldName = new Vector();
                    this.fieldType = new Vector();
                }
                this.fieldName.addElement(rsMetaData.getColumnName(i + 1));
                this.fieldType.addElement(new Integer(rsMetaData.getColumnType(i + 1)));
            }

            int index = (pageIndex - 1) * nItemsperPage;
            index = (index > 0) ? index : 0;

            if (index > 0) {
                myResult.absolute(index);
            }
            while (myResult.next()) {
                this.currentRow += 1;
                for (int i = 0; i < this.columns; ++i) {
                    if (1 == this.currentRow) {
                        this.rs[i] = new Vector();
                    }
                    this.rs[i].addElement(myResult.getString(i + 1));
                }
                this.rows = this.currentRow;
            }

            if (type == 0) {
                myResult.last();
            }
            this.recCount = myResult.getRow();

            myResult.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException localSQLException1) {
            }
        }

        this.currentRow = -1;
        return this.recCount;
    }

    public String getColName(int i) {
        if ((i < 0) || (i > this.columns)) {
            return null;
        }
        return new String(this.fieldName.get(i).toString());
    }

    public int getColCount() {
        return this.columns;
    }

    public int getRecordCounts() {
        return this.recCount;
    }

    public String getVal(int i) {
        if ((i < 0) || (i >= this.columns) || (this.currentRow < 0)) {
            return null;
        }
        try {
            return this.rs[i].get(this.currentRow).toString();
        } catch (NullPointerException nex) {
        }
        return null;
    }

    public String getVal(String fn) {
        int i = -1;
        do {
            ++i;
        } while ((i < this.columns) && (!(this.fieldName.get(i).toString().equalsIgnoreCase(fn))));
        return getVal(i);
    }

    public int getType(int i) {
        if ((i < 0) || (i >= this.columns) || (this.currentRow < 0)) {
            return 1111;
        }
        try {
            return ((Integer) this.fieldType.get(i)).intValue();
        } catch (NullPointerException nex) {
        }
        return 1111;
    }

    public int getType(String fn) {
        int i = -1;
        do {
            ++i;
        } while ((i < this.columns) && (!(this.fieldName.get(i).toString().equalsIgnoreCase(fn))));
        return getType(i);
    }

    private void move(int i) {
        if (i <= -1) {
            this.currentRow = -1;
        } else if (i >= this.rows - 1) {
            this.currentRow = (this.rows - 1);
        } else {
            this.currentRow = i;
        }
    }

    public boolean moveNext() {
        if (this.currentRow == this.rows - 1) {
            return false;
        }
        move(this.currentRow + 1);
        return true;
    }

    public boolean movePrev() {
        if (this.currentRow == -1) {
            return false;
        }
        move(this.currentRow - 1);
        return true;
    }

    public boolean moveFirst() {
        this.currentRow = -1;
        return true;
    }

    public boolean moveLast() {
        this.currentRow = (this.rows - 1);
        return true;
    }

    public boolean isAfterLast() {
        return (this.currentRow == this.rows);
    }

    public boolean isBeforeFirst() {
        return (this.currentRow == -1);
    }
}