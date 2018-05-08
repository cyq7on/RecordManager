package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.OnRecyclerViewListener;
import cn.bmob.imdemo.adapter.TableAdapter;
import cn.bmob.imdemo.adapter.base.IMutlipleItem;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.Record;
import cn.bmob.imdemo.ui.EditRecordActivity;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TableFragment extends ParentWithNaviFragment {


    @Bind(R.id.rc_view)
    RecyclerView rcView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    @Bind(R.id.et_idCard)
    EditText etIdCard;
    @Bind(R.id.et_name)
    EditText etName;
    private TableAdapter adapter;
    private PopupMenu popup;

    @Override
    protected String title() {
        return "违章记录";
    }

    @Override
    public Object right() {
        return R.drawable.base_action_bar_add_bg_selector;
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {
                popup.show();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_table, container, false);
        ButterKnife.bind(this, rootView);
        initNaviView();
        IMutlipleItem<Record> mutlipleItem = new IMutlipleItem<Record>() {

            @Override
            public int getItemViewType(int postion, Record Record) {
                if (postion == 0) {
                    return TableAdapter.TYPE_HEAD;
                } else {
                    return TableAdapter.TYPE_CONTEN;
                }
            }

            @Override
            public int getItemLayoutId(int viewtype) {
                if (viewtype == TableAdapter.TYPE_HEAD) {
                    return R.layout.item_table;
                } else {
                    return R.layout.item_table_content;
                }
            }

            @Override
            public int getItemCount(List<Record> list) {
                return list.size() + 1;
            }
        };
        adapter = new TableAdapter(getActivity(), mutlipleItem, null);
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Record record = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record",record);
                startActivity(EditRecordActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query(false);
            }
        });

        popup = new PopupMenu(getActivity(),tv_right);
        popup.getMenuInflater().inflate(R.menu.menu_search, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.action1:
                        query(false);
                        return true;
                    case R.id.action2:
                        query(1);
                        return true;
                    case R.id.action3:
                        query(2);
                        return true;
                    case R.id.action4:
                        query(etIdCard.getText().toString(),etName.getText().toString());
                        return true;
                    case R.id.action5:
                        query(true);
                        return true;
                }
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        query(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void query(String idCard,String name) {
        swRefresh.setRefreshing(true);
        BmobQuery<Record> query = new BmobQuery<>();
        query.order("-updatedAt");
        if(!TextUtils.isEmpty(idCard)){
            query.addWhereEqualTo("idCard",idCard);
        }
        if(!TextUtils.isEmpty(name)){
            query.addWhereEqualTo("name",name);
        }

        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> list, BmobException e) {
                swRefresh.setRefreshing(false);
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        adapter.bindDatas(list);
                    } else {
                        if (getUserVisibleHint()) {
                            adapter.bindDatas(new ArrayList<Record>());
                            toast("暂无信息");
                        }
                    }
                } else {
                    if (getUserVisibleHint()) {
                        toast("获取信息出错");
                    }
                    Logger.e(e);
                }
            }
        });
    }

    private void query(int status) {
        swRefresh.setRefreshing(true);
        BmobQuery<Record> query = new BmobQuery<>();
        query.order("-updatedAt");
        query.addWhereEqualTo("status",status);
        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> list, BmobException e) {
                swRefresh.setRefreshing(false);
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        adapter.bindDatas(list);
                    } else {
                        if (getUserVisibleHint()) {
                            adapter.bindDatas(new ArrayList<Record>());
                            toast("暂无信息");
                        }
                    }
                } else {
                    if (getUserVisibleHint()) {
                        toast("获取信息出错");
                    }
                    Logger.e(e);
                }
            }
        });
    }

    private void query(boolean isBlack) {
        swRefresh.setRefreshing(true);
        BmobQuery<Record> query = new BmobQuery<>();
        if(isBlack){
            query.addWhereEqualTo("score",12);
        }
        query.order("-updatedAt");
        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> list, BmobException e) {
                swRefresh.setRefreshing(false);
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        adapter.bindDatas(list);
                    } else {
                        if (getUserVisibleHint()) {
                            adapter.bindDatas(new ArrayList<Record>());
                            toast("暂无信息");
                        }
                    }
                } else {
                    if (getUserVisibleHint()) {
                        toast("获取信息出错");
                    }
                    Logger.e(e);
                }
            }
        });
    }
}
