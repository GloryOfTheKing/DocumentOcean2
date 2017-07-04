package org.fms.mysql.entity;


import javax.persistence.*;

@Entity
@Table(name = "state")
public class State implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StateID;
    private String StateName;
    private String StateDesc;

    public State() {
    }

    public Long getStateID() {
        return StateID;
    }

    public void setStateID(Long stateID) {
        StateID = stateID;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getStateDesc() {
        return StateDesc;
    }

    public void setStateDesc(String stateDesc) {
        StateDesc = stateDesc;
    }
}
