package com.trello.pogo;

public class PogoCardBody {
    private String name;
    private String desc;
    private String idList;

    public PogoCardBody() {
    }

    public PogoCardBody(String name, String desc, String idList) {
        this.name = name;
        this.desc = desc;
        this.idList = idList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }
}
