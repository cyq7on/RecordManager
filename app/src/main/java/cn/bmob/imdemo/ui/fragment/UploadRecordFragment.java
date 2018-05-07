package cn.bmob.imdemo.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.Record;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;

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
    @Bind(R.id.et_image) android.widget.TextView etImage;@Bind(R.id.tv_date) android.widget.TextView tvDate;@Bind(R.id.et_break_type) EditText etBreakType;@Bind(R.id.et_break_place) EditText etBreakPlace;@Bind(R.id.et_fee) EditText etFee;
    private static final int REQUEST_CODE = 0x1001;
    private String url;

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
        etImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvDate.setText(i + "-" + (i1 + 1)  + "-" + i2);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = etName.getText().toString();
                final String idCard = etIdCard.getText().toString();
                final String plate = etPlate.getText().toString();
                final String score = etScore.getText().toString();
                final String type = etType.getText().toString();
                final String tel = etTel.getText().toString();
                String image = etImage.getText().toString();
                final String date = tvDate.getText().toString();
                final String breakType = etBreakType.getText().toString();
                final String breakPlace = etBreakPlace.getText().toString();
                final String fee = etFee.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(idCard) || TextUtils.isEmpty(plate)
                        || TextUtils.isEmpty(score) || TextUtils.isEmpty(type)
                        || TextUtils.isEmpty(tel)|| TextUtils.isEmpty(image)|| TextUtils.isEmpty(date)
                        || TextUtils.isEmpty(breakPlace)|| TextUtils.isEmpty(breakType)
                        || TextUtils.isEmpty(fee)){
                    toast("请将信息填写完整");
                    return;
                }
                final BmobFile bmobFile = new BmobFile(new File(url));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Record record = new Record();
                            record.name = name;
                            record.idCard = idCard;
                            record.plateNum = plate;
                            record.score = Integer.parseInt(score);
                            record.type = type;
                            record.tel = tel;
                            record.image = bmobFile;
                            record.date = date;
                            record.breakType = breakType;
                            record.breakPlace = breakPlace;
                            record.fee = fee;
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

    private void selectPhoto() {
        int color = getResources().getColor(R.color.colorPrimaryDark);
        // 自由配置选项
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(color)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(color)
                // 裁剪大小。needCrop为true的时候配置
//                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(9)
                .build();

        // 跳转到图片选择器
        ISNav.getInstance().toListActivity(this, config, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path : pathList) {
                Logger.d(path + "\n");
            }
            if (pathList.size() > 0) {
                url = pathList.get(0);
                File file = new File(url);
                etImage.setText(file.getName());
            }
        }
    }
}
