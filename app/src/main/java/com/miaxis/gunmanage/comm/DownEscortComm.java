package com.miaxis.gunmanage.comm;

import com.miaxis.gunmanage.bean.Escort;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by xu.nan on 2017/6/30.
 */

public class DownEscortComm extends BaseComm {

    private static final short FUNC_REQ_DOWN_ESCORT = 120;
    private static final short FUNC_RET_DOWN_ESCORT = 8120;

    private List<Escort> escortList;
    private String orgCode;

    public List<Escort> getEscortList() {
        return escortList;
    }

    public DownEscortComm(Socket socket, String orgCode) {
        super(socket, FUNC_REQ_DOWN_ESCORT, FUNC_RET_DOWN_ESCORT);
        this.orgCode = orgCode;
        escortList = new ArrayList<>();
    }

    @Override
    protected Vector<Byte> MakePackBody() {
        Vector<Byte> data = new Vector<Byte>();
        data.clear();
        data.addAll(MakeField(orgCode));
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
        for (int i=0; i<nCount; i++)
        {
            String str;
            Escort escort = new Escort();
            escort.setId(i);
            int[] length = new int[1];

            str = parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
//            escort.setId(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            escort.setCode(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            escort.setName(str);
            index += length[0];

            str = this.parseFiled(data, index, length);
            if (str == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            escort.setPassword(str);
            index += length[0];

            byte[] photo = this.parseByteFiled(data, index, length);
            if (photo == null)
            {
                message = "数据包体错误!";
                return -6;
            }
            escort.setPhoto(photo);

            index += length[0];
            for (int m=0; m<10; m++)
            {
                str = this.parseFiled(data, index, length);
                if (str == null)
                {
                    message = "数据包体错误!";
                    return -6;
                }
                escort.setFinger(str, m);
                index += length[0];
            }
            escortList.add(escort);
        }
        return 0;
    }
}
