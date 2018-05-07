package cn.bmob.imdemo.adapter;

import android.content.Context;

import java.util.Collection;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.base.BaseRecyclerAdapter;
import cn.bmob.imdemo.adapter.base.BaseRecyclerHolder;
import cn.bmob.imdemo.adapter.base.IMutlipleItem;
import cn.bmob.imdemo.bean.Record;

/**
 * 联系人
 * 一种简洁的Adapter实现方式，可用于多种Item布局的recycleView实现，不用再写ViewHolder啦
 *
 * @author :smile
 * @project:ContactNewAdapter
 * @date :2016-04-27-14:18
 */
public class TableAdapter extends BaseRecyclerAdapter<Record> {
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_CONTEN = 1;

    private String[] titles = {"ID", "姓名", "身份证号", "车牌号", "车牌照", "分数", "驾照类型", "电话号码"
            , "时间", "违规类型", "违规地点", "费用"};

    public TableAdapter(Context context, IMutlipleItem<Record> items, Collection<Record> datas) {
        super(context, items, datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, Record record, int position) {
        if (holder.layoutId == R.layout.item_table) {
            holder.setText(R.id.tv_id, titles[0]);
            holder.setText(R.id.tv_name, titles[1]);
            holder.setText(R.id.tv_id_card, titles[2]);
            holder.setText(R.id.tv_plate_num, titles[3]);
            holder.setText(R.id.tv_image, titles[4]);
            holder.setText(R.id.tv_score, titles[5]);
            holder.setText(R.id.tv_type, titles[6]);
            holder.setText(R.id.tv_tel, titles[7]);
            holder.setText(R.id.tv_date, titles[8]);
            holder.setText(R.id.tv_break_type, titles[9]);
            holder.setText(R.id.tv_break_place, titles[10]);
            holder.setText(R.id.tv_fee, titles[11]);
        } else {
            holder.setText(R.id.tv_id, record.getObjectId());
            holder.setText(R.id.tv_name, record.name);
            holder.setText(R.id.tv_id_card, record.idCard);
            holder.setText(R.id.tv_plate_num, record.plateNum);
//            holder.setImageView(record.image.getFileUrl(),R.mipmap.ic_launcher,R.id.iv);
            holder.setText(R.id.tv_score, String.valueOf(record.score));
            holder.setText(R.id.tv_type, record.type);
            holder.setText(R.id.tv_tel, record.tel);
            holder.setText(R.id.tv_date, record.date);
            holder.setText(R.id.tv_break_type, record.breakType);
            holder.setText(R.id.tv_break_place, record.breakPlace);
            holder.setText(R.id.tv_fee, record.fee);
        }
    }

}
