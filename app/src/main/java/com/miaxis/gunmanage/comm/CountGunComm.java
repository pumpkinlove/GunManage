package com.miaxis.gunmanage.comm;

import java.net.Socket;
import java.util.Vector;

/**
 * Created by xu.nan on 2017/7/3.
 */

public class CountGunComm extends BaseComm {
    private static final short FUNC_REQ_DOWN_GUN = 124;
    private static final short FUNC_RET_DOWN_GUN = 8124;

    private int gunCount;
    private String orgCode;
    private String timeStamp;

    public int getGunCount() {
        return gunCount;
    }

    public CountGunComm(Socket socket, String orgCode, String timeStamp) {
        super(socket, FUNC_REQ_DOWN_GUN, FUNC_RET_DOWN_GUN);
        this.orgCode = orgCode;
        this.timeStamp = timeStamp;
    }

    @Override
    protected Vector<Byte> MakePackBody() {
        Vector<Byte> data = new Vector<>();
        data.clear();
        data.addAll(MakeField(orgCode));
        data.addAll(MakeField(timeStamp));
        return data;
    }

    @Override
    protected int fetchData(byte[] data) {
        int result = bytestous(data);
        if (result == ERROR_DBACCESS) {
            message = "服务器访问数据库错误!";
            return -100;
        }
        if (result != 0) {
            message = "未知应答码["+result+"]";
            return -5;
        }

        gunCount = data[2];
        if (gunCount < 0) {
            gunCount += 256;
        }

        return 0;
    }
}
