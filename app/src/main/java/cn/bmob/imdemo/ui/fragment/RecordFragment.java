package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.feng.fixtablelayout.FixTableLayout;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.RecordAdapter;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.Record;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RecordFragment extends ParentWithNaviFragment {


    @Bind(R.id.fixTableLayout)
    FixTableLayout fixTableLayout;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    private String[] titles = {"ID", "姓名", "身份证号", "车牌号", "分数", "驾照类型", "电话号码"};

    @Override
    protected String title() {
        return "违章记录";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, rootView);
        initNaviView();
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        query();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void query() {
        swRefresh.setRefreshing(true);
        BmobQuery<Record> query = new BmobQuery<>();
        query.order("-updatedAt");
        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> list, BmobException e) {
                swRefresh.setRefreshing(false);
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        fixTableLayout.setAdapter(new RecordAdapter(titles,list));
                    } else {
                        if(getUserVisibleHint()){
                            toast("暂无信息");
                        }
                    }
                } else {
                    if(getUserVisibleHint()){
                        toast("获取信息出错");
                    }
                    Logger.e(e);
                }
            }
        });
    }
}
