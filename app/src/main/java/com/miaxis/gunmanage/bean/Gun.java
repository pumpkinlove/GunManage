package com.miaxis.gunmanage.bean;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import freemarker.template.utility.StringUtil;

/**
 * Created by xu.nan on 2017/6/30.
 */
@Entity
public class Gun {
    @Id
    private long id;
    private String code;
    private String rfid;
    private String opDate;
    private String opUserName;
    private String status;          // 1 未领取 2 已领取
    private String statusName;
    private String compCode;
    private String compNo;
    private String comId;
    private String type;

    @Generated(hash = 1140872077)
    public Gun(long id, String code, String rfid, String opDate, String opUserName,
            String status, String statusName, String compCode, String compNo,
            String comId, String type) {
        this.id = id;
        this.code = code;
        this.rfid = rfid;
        this.opDate = opDate;
        this.opUserName = opUserName;
        this.status = status;
        this.statusName = statusName;
        this.compCode = compCode;
        this.compNo = compNo;
        this.comId = comId;
        this.type = type;
    }

    @Generated(hash = 398662952)
    public Gun() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        if (TextUtils.isEmpty(statusName)) {
            if ("1".equals(status)) {
                return "已领取";
            } else if ("2".equals(status)) {
                return "未领取";
            }
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getCompNo() {
        return compNo;
    }

    public void setCompNo(String compNo) {
        this.compNo = compNo;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
