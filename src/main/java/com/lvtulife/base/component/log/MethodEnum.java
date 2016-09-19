package com.lvtulife.base.component.log;

/**
 * Created by Administrator on 2016/6/2 0002.
 */
public enum MethodEnum {
    Find("查询", 0), Create("新增", 1), Update("编辑", 2), Delete("删除", 3);

    private String name;
    private Byte value;

    MethodEnum(String name, Integer value) {
        this.name = name;
        this.value = value.byteValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }
}
