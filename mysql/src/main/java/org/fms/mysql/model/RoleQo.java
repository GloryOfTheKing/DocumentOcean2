package org.fms.mysql.model;

/**
 * Created by lion on 2017/8/9.
 */
public class RoleQo extends PageQo{
    private Long id;
    private String name;
    private String info;

    public RoleQo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
