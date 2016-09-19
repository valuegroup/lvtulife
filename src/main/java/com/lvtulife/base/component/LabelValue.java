package com.lvtulife.base.component;

import java.io.Serializable;

public class LabelValue implements Serializable {
    private String label = null;
    private String colorLabel = null;
    private String value = null;

    public LabelValue() {
    }

    public LabelValue(String label, String value) {
        this.label = label;
        this.value = value;
        this.colorLabel = "";
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColorLabel() {
        return colorLabel;
    }

    public void setColorLabel(String colorLabel) {
        this.colorLabel = colorLabel;
    }

    public int compareTo(Object o) {
        String otherLabel = ((LabelValue) o).getLabel();
        return this.getLabel().compareTo(otherLabel);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("LabelValue[");
        sb.append(this.label);
        sb.append(", ");
        sb.append(this.value);
        sb.append("]");
        return (sb.toString());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof LabelValue)) {
            return false;
        }

        LabelValue bean = (LabelValue) obj;
        int nil = (this.getValue() == null) ? 1 : 0;
        nil += (bean.getValue() == null) ? 1 : 0;

        if (nil == 2) {
            return true;
        } else if (nil == 1) {
            return false;
        } else {
            return this.getValue().equals(bean.getValue());
        }
    }

    public int hashCode() {
        return (this.getValue() == null) ? 17 : this.getValue().hashCode();
    }
}
