package cn.bmob.imdemo.adapter;

import android.widget.TextView;

import com.app.feng.fixtablelayout.inter.IDataAdapter;

import java.util.List;

import cn.bmob.imdemo.bean.Record;

public class RecordAdapter implements IDataAdapter {

    private String[] titles;
    private List<Record> data;

    public RecordAdapter(String[] titles,List<Record> data) {
        this.titles = titles;
        this.data = data;
    }

    public void setData(List<Record> data) {
        this.data = data;
    }


    @Override
    public String getTitleAt(int pos) {
        return titles[pos];
    }

    @Override
    public int getTitleCount() {
        return titles.length;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void convertData(int i, List<TextView> list) {
        Record record = data.get(i);
        list.get(1).setText(record.name);
        list.get(2).setText(record.idCard);
        list.get(3).setText(record.plateNum);
        list.get(4).setText(String.valueOf(record.score));
        list.get(5).setText(record.type);
        list.get(6).setText(record.tel);
    }

    @Override
    public void convertLeftData(int i, TextView textView) {
        textView.setText(data.get(i).getObjectId());
    }
}
