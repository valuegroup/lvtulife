package com.lvtulife.system.component.onekeyBuild;

public class Info extends EnvQuery {
    private String Sql = "";
    private int page = 1;
    private int pages = -1;
    private int type = 0;
    private int itemsPerpage = 20;

    public void query() {
        int count = select(getSql(), this.page, this.itemsPerpage, this.type);
        this.pages = ((count - 1 + this.itemsPerpage) / this.itemsPerpage);
    }

    public String getSql() {
        return Sql;
    }

    public void setSql(String sql) {
        Sql = sql;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getItemsPerpage() {
        return itemsPerpage;
    }

    public void setItemsPerpage(int itemsPerpage) {
        this.itemsPerpage = itemsPerpage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
