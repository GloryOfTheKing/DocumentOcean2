package org.fms.mysql.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String info;
    private String url;
    private String mehtod;

    @ManyToMany(mappedBy = "permissions", fetch=FetchType.EAGER)
    @JsonBackReference
    private Set<Role> roles;

    public Permission() {
    }

    public Permission(String name, String info, String url, String mehtod) {
        this.name = name;
        this.info = info;
        this.url = url;
        this.mehtod = mehtod;
    }

    public long getId() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMehtod() {
        return mehtod;
    }

    public void setMehtod(String mehtod) {
        this.mehtod = mehtod;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
