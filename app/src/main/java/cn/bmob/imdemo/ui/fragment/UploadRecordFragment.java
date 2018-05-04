package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.Record;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UploadRecordFragment extends ParentWithNaviFragment {
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_idCard)
    EditText etIdCard;
    @Bind(R.id.et_plate)
    EditText etPlate;
    @Bind(R.id.et_score)
    EditText etScore;
    @Bind(R.id.et_type)
    EditText etType;
    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.btn_upload)
    Button btnUpload;

    @Override
    protected String title() {
        return "上传";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upload_record, container, false);
        ButterKnife.bind(this, rootView);
        initNaviView();
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String idCard = etIdCard.getText().toString();
                String plate = etPlate.getText().toString();
                String score = etScore.getText().toString();
                String type = etType.getText().toString();
                String tel = etTel.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(idCard) || TextUtils.isEmpty(plate)
                        || TextUtils.isEmpty(score) || TextUtils.isEmpty(type)
                        || TextUtils.isEmpty(tel)){
                    toast("请将信息填写完整");
                    return;
                }
                Record record = new Record();
                record.name = name;
                record.idCard = idCard;
                record.plateNum = plate;
                record.score = Integer.parseInt(score);
                record.type = type;
                record.tel = tel;
                record.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            toast("上传成功");
                        } else {
                            toast("上传失败");
                            Logger.d(e.getMessage());
                        }
                    }
                });
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
