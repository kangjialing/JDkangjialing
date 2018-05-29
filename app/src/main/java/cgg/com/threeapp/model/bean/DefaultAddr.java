package cgg.com.threeapp.model.bean;

/**
 * author: Wanderer
 * date:   2018/3/21 18:41
 * email:  none
 */

public class DefaultAddr {
    /**
     * msg : 请求成功
     * code : 0
     * data : {"addr":"福建省 泉州市 德化县 百威 移动通信","addrid":1059,"mobile":15110009666,"name":"流逼鹏","status":1,"uid":12290}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * addr : 福建省 泉州市 德化县 百威 移动通信
         * addrid : 1059
         * mobile : 15110009666
         * name : 流逼鹏
         * status : 1
         * uid : 12290
         */

        private String addr;
        private int addrid;
        private long mobile;
        private String name;
        private int status;
        private int uid;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getAddrid() {
            return addrid;
        }

        public void setAddrid(int addrid) {
            this.addrid = addrid;
        }

        public long getMobile() {
            return mobile;
        }

        public void setMobile(long mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
