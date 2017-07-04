package org.fms.mysql.entity;

import javax.persistence.*;

@Entity
@Table(name = "department")
public class Department implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DeptID;
    private String DeptName;
    private String DeptDesc;
    private Long SupDept;

    public Department() {
    }

    public Long getDeptID() {
        return DeptID;
    }

    public void setDeptID(Long deptID) {
        DeptID = deptID;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getDeptDesc() {
        return DeptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        DeptDesc = deptDesc;
    }

    public Long getSupDept() {
        return SupDept;
    }

    public void setSupDept(Long supDept) {
        SupDept = supDept;
    }
}
