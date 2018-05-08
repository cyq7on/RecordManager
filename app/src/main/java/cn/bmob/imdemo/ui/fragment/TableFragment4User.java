package cn.bmob.imdemo.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.orhanobut.logger.Logger;

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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class TableFragment4User extends ParentWithNaviFragment {


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
        etIdCard.setVisibility(View.GONE);
        etName.setVisibility(View.GONE);
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

            }

            @Override
            public boolean onItemLongClick(final int position) {
                final AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity()).setTitle("是否申请撤销此记录？").
                        setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Record record = adapter.getItem(position);
                                record.status = 1;
                                record.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            toast("确认成功");
                                        } else {
                                            toast("确认失败");
                                            Logger.d(e.getMessage());
                                        }
                                    }
                                });
                            }
                        }).setNegativeButton("取消", null);
                builder.show();
                return true;
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
        popup.getMenuInflater().inflate(R.menu.menu_search_user, popup.getMenu());
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


    private void query(int status) {
        swRefresh.setRefreshing(true);
        BmobQuery<Record> query = new BmobQuery<>();
        query.order("-updatedAt");
        query.addWhereEqualTo("idCard",user.idCard);
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
                            toast("您还没有相应记录哦");
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
        query.addWhereEqualTo("idCard",user.idCard);
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
                            toast("您还没有违章记录哦");
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
