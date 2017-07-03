package com.miaxis.gunmanage.comm;

import com.miaxis.gunmanage.bean.Gun;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by xu.nan on 2017/6/30.
 */

public class DownGunComm extends BaseComm {
    private static final short FUNC_REQ_DOWN_GUN = 121;
    private static final short FUNC_RET_DOWN_GUN = 8121;

    private List<Gun> gunList;
    private String orgCode;
    private String pageNum;
    private String pageSize;
    private String timeStamp;

    public List<Gun> getGunList() {
        return gunList;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public DownGunComm(Socket socket, String pageNum, String orgCode, String timeStamp, String pageSize) {
        super(socket, FUNC_REQ_DOWN_GUN, FUNC_RET_DOWN_GUN);
        this.orgCode = orgCode;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.timeStamp = timeStamp;
        gunList = new ArrayList<>();
    }

    @Override
    protected Vector<Byte> MakePackBody() {
        Vector<Byte> data = new Vector<>();
        data.clear();
        data.addAll(MakeField(orgCode));
        data.addAll(MakeField(timeStamp));
        data.addAll(MakeField(pageNum));
        data.addAll(MakeField(pageSize));
        return data;
    }

    @Override
    protected int fetchData(byte[] data) {
        int result = bytestous(data);
        if (result == ERROR_DBACCESS)
        {
            message = "服务器访问数据库错误!";
            return -100;
        }
        if (result != 0)
        {
            message = "未知应答码["+result+"]";
            return -5;
        }

        int nCount = data[2];
        if (nCount < 0)
        {
            nCount += 256;
        }

        int index = 3;

        int[] len = new int[1];

        timeStamp = parseFiled(data, index, len);
        if (timeStamp == null)
        {
            message = "数据包体错误!";
            return -6;
        }
        index += len[0];

        for (int i=0; i<nCount; i++) {
            String str;
            Gun gun = new Gun();
            int[] length = new int[1];

            str = parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setId(Integer.valueOf(str));
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setCode(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setRfid(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setStatus(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setStatusName(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setComId(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setCompCode(str);
            index += length[0];


            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setOpUserName(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            gun.setOpDate(str);

            index += length[0];
            gunList.add(gun);
        }
        return 0;
    }
}
