package com.example.favornotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.favornotes.adapter.AccountAdapter;
import com.example.favornotes.db.AccountBean;
import com.example.favornotes.db.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView todayLv;  //展示今日收支情况的ListView
    private ImageView ic_tab_add;
    ImageView searchIv;
    CalendarView mcalendarView;
    //声明数据源
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month,day;


    //主页面 显示日历窗口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        initView();
        mDatas = new ArrayList<>();
        //设置适配器：加载每一行数据到列表当中
        adapter = new AccountAdapter(this, mDatas);
        todayLv.setAdapter(adapter);
    }

    /** 初始化自带的View的方法*/
    private void initView() {
        todayLv = findViewById(R.id.main_lv);
        searchIv = findViewById(R.id.main_iv_search);
        mcalendarView = findViewById(R.id.calendarView);
        mcalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //显示用户选择的日期
                MainActivity.this.year = year;
                MainActivity.this.month = month +1;
                MainActivity.this.day = dayOfMonth;
                onResume();
            }
        });
        ic_tab_add = (ImageView) findViewById(R.id.dd5);
        ic_tab_add.setOnClickListener(this);
        setLVLongClickListener();
    }
    /** 设置ListView的长按事件*/
    private void setLVLongClickListener() {
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AccountBean clickBean = mDatas.get(position);  //获取正在被点击的这条信息

                //弹出提示用户是否删除的对话框
                showDeleteItemDialog(clickBean);
                return false;
            }
        });
    }
    /* 弹出是否删除某一条记录的对话框*/
    private void showDeleteItemDialog(final  AccountBean clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int click_id = clickBean.getId();
                        //执行删除的操作
                        DBManager.deleteItemFromAccounttbById(click_id);
                        mDatas.remove(clickBean);   //实时刷新，移除集合当中的对象
                        adapter.notifyDataSetChanged();   //提示适配器更新数据
                    }
                });
        builder.create().show();   //显示对话框
    }

    @Override
    public void onClick( View v) {
        switch (v.getId()) {
            case R.id.dd5:
                showPoupWindow();
                break;
            case R.id.main_iv_search:
                Toast.makeText(MainActivity.this, "点击了搜索", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(MainActivity.this, SearchActivity.class);  //跳转界面
                startActivity(it);
                break;
            case R.id.dd:
                Toast.makeText(MainActivity.this, "当前页面已经是主页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.given_btn:
                Toast.makeText(MainActivity.this, "点击了随礼", Toast.LENGTH_SHORT).show();
                Intent it2 = new Intent(MainActivity.this, GivenActivity.class);  //跳转界面
                startActivity(it2);
                break;
            case R.id.rec_btn:
                Toast.makeText(MainActivity.this, "点击了礼簿", Toast.LENGTH_SHORT).show();
                Intent it3 = new Intent(MainActivity.this, ReceivedActivity.class);  //跳转界面
                startActivity(it3);
                break;
            case R.id.statistics:
                Toast.makeText(MainActivity.this, "点击了统计", Toast.LENGTH_SHORT).show();
                Intent it4 = new Intent(MainActivity.this, StatsActivity.class);  //跳转界面
                startActivity(it4);
                break;
        }
    }

    private void showPoupWindow() {
        MyPopupWindow mPopupWindow = new MyPopupWindow(MainActivity.this,
                new MyPopupWindow.OnPopWindowClickListener() {
                    @Override
                    public void onPopWindowClickListener(View view) {
                        switch (view.getId()) {
                            case R.id .iv_push_myrecord:
                                Toast.makeText(MainActivity.this, "点击了记录", Toast.LENGTH_SHORT).show();
                                Intent it = new Intent(MainActivity.this, RecordActivity.class);  //跳转界面
                                startActivity(it);
                                break;
                            case R.id.iv_push_mysetting:
                                Toast.makeText(MainActivity.this, "点击了设置", Toast.LENGTH_SHORT).show();
                                Intent it1 = new Intent(MainActivity.this, SettingActivity.class);  //跳转界面
                                startActivity(it1);
                                break;
                        }
                    }
                });
        mPopupWindow.show();
    }

    // 当activity获取焦点时，会调用的方法
    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();

    }

    // 加载数据库数据
    private void loadDBData() {
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year, month, day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
        

    }
    /* 获取今日的具体时间*/
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
}