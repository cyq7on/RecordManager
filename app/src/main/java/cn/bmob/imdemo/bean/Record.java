package cn.bmob.imdemo.bean;

import cn.bmob.v3.BmobObject;

public class Record extends BmobObject {
    public String name;
    public String idCard;
    public String plateNum;
    public int score;
    public String type;
    public String tel;

    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", score=" + score +
                ", type='" + type + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
