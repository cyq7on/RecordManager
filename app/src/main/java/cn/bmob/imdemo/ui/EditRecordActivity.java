package cn.bmob.imdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ImageLoaderFactory;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.Record;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EditRecordActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_idCard)
    EditText etIdCard;
    @Bind(R.id.et_plate)
    EditText etPlate;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.et_score)
    EditText etScore;
    @Bind(R.id.et_type)
    EditText etType;
    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.tv_date)
    EditText tvDate;
    @Bind(R.id.et_break_type)
    EditText etBreakType;
    @Bind(R.id.et_break_place)
    EditText etBreakPlace;
    @Bind(R.id.et_fee)
    EditText etFee;
    @Bind(R.id.btn_update)
    Button btnUpdate;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_agree)
    Button btnAgree;

    @Override
    protected String title() {
        return "编辑记录";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        ButterKnife.bind(this);
        Bundle bundle = getBundle();
        final Record record = (Record) bundle.getSerializable("record");
        Logger.d(record.toString());
        etName.setText(record.name);
        etIdCard.setText(record.idCard);
        etPlate.setText(record.plateNum);
        ImageLoaderFactory.getLoader().loadAvator(iv,record.image.getFileUrl(), R.mipmap.ic_launcher);
        tvDate.setText(record.date);
        etType.setText(record.type);
        etScore.setText(String.valueOf(record.score));
        etTel.setText(record.tel);
        etBreakPlace.setText(record.breakPlace);
        etBreakType.setText(record.breakType);
        etFee.setText(record.fee);
        if(record.status != 1){
            btnAgree.setVisibility(View.GONE);
        }
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record.status = 2;
                record.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            toast("撤销成功");
                            finish();
                        } else {
                            toast("撤销失败");
                            Logger.d(e.getMessage());
                        }
                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record.name = etName.getText().toString();
                record.idCard = etIdCard.getText().toString();
                record.plateNum = etPlate.getText().toString();
                record.date = tvDate.getText().toString();
                record.tel = etTel.getText().toString();
                record.type = etType.getText().toString();
                record.score = Integer.parseInt(etScore.getText().toString());
                record.breakPlace = etBreakPlace.getText().toString();
                record.breakType = etBreakType.getText().toString();
                record.fee = etFee.getText().toString();
                record.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            toast("更新成功");
                            finish();
                        } else {
                            toast("更新失败");
                            Logger.d(e.getMessage());
                        }
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record.delete(record.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            toast("删除成功");
                            finish();
                        } else {
                            toast("删除失败");
                            Logger.d(e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
