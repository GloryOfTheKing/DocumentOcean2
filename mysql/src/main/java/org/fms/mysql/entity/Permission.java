package org.fms.mysql.entity;

import javax.persistence.*;


@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PermID;
    private String PermName;
    private String PermDesc;
    private String Url;
    private String PermMehtod;

    public Permission() {
    }

    public long getPermID() {
        return PermID;
    }

    public void setPermID(Long permID) {
        PermID = permID;
    }

    public String getPermName() {
        return PermName;
    }

    public void setPermName(String permName) {
        PermName = permName;
    }

    public String getPermDesc() {
        return PermDesc;
    }

    public void setPermDesc(String permDesc) {
        PermDesc = permDesc;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getPermMehtod() {
        return PermMehtod;
    }

    public void setPermMehtod(String permMehtod) {
        PermMehtod = permMehtod;
    }
}
