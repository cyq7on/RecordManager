package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import sysu.zyb.panellistlibrary.AbstractPanelListAdapter;
import sysu.zyb.panellistlibrary.PanelListLayout;

public class RecordFragment extends ParentWithNaviFragment {

    private PanelListLayout pl_root;
    private ListView lv_content;

    private AbstractPanelListAdapter adapter;

    private List<List<String>> contentList = new ArrayList<>();

    private List<Integer> itemWidthList = new ArrayList<>();

    private List<String> rowDataList = new ArrayList<>();
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
        initView();

        initRowDataList();
        initContentDataList();
        initItemWidthList();

        adapter = new AbstractPanelListAdapter(getContext(),pl_root,lv_content) {
            @Override
            protected BaseAdapter getContentAdapter() {
                return null;
            }
        };
        adapter.setInitPosition(10);
        adapter.setSwipeRefreshEnabled(true);
        adapter.setRowDataList(rowDataList);
        adapter.setTitle("example");
        adapter.setOnRefreshListener(new CustomRefreshListener());
        adapter.setContentDataList(contentList);
        adapter.setItemWidthList(itemWidthList);
        adapter.setItemHeight(40);
        pl_root.setAdapter(adapter);
        return rootView;
    }

    private void initView() {

        pl_root = (PanelListLayout) rootView.findViewById(R.id.id_pl_root);
        lv_content = (ListView) rootView.findViewById(R.id.id_lv_content);

        //设置listView为多选模式，长按自动触发
        lv_content.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//        lv_content.setMultiChoiceModeListener(new MultiChoiceModeCallback());

        //listView的点击监听
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast("你选中的position为：" + position);
            }
        });
    }

    public class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            // you can do sth here, for example: make a toast:
            toast("custom SwipeRefresh listener");
            // don`t forget to call this
            adapter.getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    /** 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private void initRowDataList(){
        rowDataList.add("第一列");
        rowDataList.add("第二列");
        rowDataList.add("第三列");
        rowDataList.add("第四列");
        rowDataList.add("第五列");
        rowDataList.add("第六列");
        rowDataList.add("第七列");
    }

    /**
     * 初始化content数据
     */
    private void initContentDataList() {
        for (int i = 1; i <= 50; i++) {
            List<String> data = new ArrayList<>();
            data.add("第" + i + "行第一个");
            data.add("第" + i + "行第二个");
            data.add("第" + i + "行第三个");
            data.add("第" + i + "行第四个");
            data.add("第" + i + "行第五个");
            data.add("第" + i + "行第六个");
            data.add("第" + i + "行第七个");
            contentList.add(data);
        }
    }

    /**
     * 初始化 content 部分每一个 item 的每个数据的宽度
     */
    private void initItemWidthList(){
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
    }

    /**
     * 更新content数据源
     */
    private void changeContentDataList() {
        contentList.clear();
        for (int i = 1; i < 500; i++) {
            List<String> data = new ArrayList<>();
            data.add("第" + i + "第一个");
            data.add("第" + i + "第二个");
            data.add("第" + i + "第三个");
            data.add("第" + i + "第四个");
            data.add("第" + i + "第五个");
            data.add("第" + i + "第六个");
            data.add("第" + i + "第七个");
            contentList.add(data);
        }
    }

    /**
     * 插入一个数据
     */
    private void insertData(){
        List<String> data = new ArrayList<>();
        data.add("插入1");
        data.add("插入2");
        data.add("插入3");
        data.add("插入4");
        data.add("插入5");
        data.add("插入6");
        data.add("插入7");
        contentList.add(5,data);
    }

    /**
     * 删除一个数据
     */
    private void removeData(){
        contentList.remove(10);
    }
    
}
