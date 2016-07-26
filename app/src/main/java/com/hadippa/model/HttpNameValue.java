package com.hadippa.model;

import java.io.File;

/**
 * Created by vishal on 1/10/15.
 */
public class HttpNameValue {

    public static final int TEXT = 1;

    public static final int FILE = 2;

    private String name="";

    private String value="";

    private File file;

    private int type = HttpNameValue.TEXT;

    public HttpNameValue(int type, String name, String value, File file){
        this.type = type;
        this.name = name;
        this.value = value;
        this.file = file;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
