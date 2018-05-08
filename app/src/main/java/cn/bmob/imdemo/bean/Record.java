package cn.bmob.imdemo.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Record extends BmobObject {
    public String name;
    public String idCard;
    public String plateNum;
    public int score;
    public String type;
    public String tel;
    public BmobFile image ;
    public String date ;
    public String breakType;
    public String breakPlace;
    public String fee;
    public int status = 0;

    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", score=" + score +
                ", type='" + type + '\'' +
                ", tel='" + tel + '\'' +
                ", image=" + image.getFileUrl() +
                ", date='" + date + '\'' +
                ", breakType='" + breakType + '\'' +
                ", breakPlace='" + breakPlace + '\'' +
                ", fee='" + fee + '\'' +
                '}';
    }
}
