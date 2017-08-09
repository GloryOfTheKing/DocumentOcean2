package org.fms.web.utils;

/**
 * Created by lion on 2017/8/9.
 */
public class Results {
    public Results(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /*状态码*/
    private Integer code;

    /*提示信息*/
    private String msg;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
