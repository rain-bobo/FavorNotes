package com.example.favornotes.frag_record;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.favornotes.R;
import com.example.favornotes.adapter.TypeBaseAdapter;
import com.example.favornotes.db.AccountBean;
import com.example.favornotes.db.TypeBean;
import com.example.favornotes.utils.SelectTimeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 记录页面当中的支出模块
 */
public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    EditText moneyEt,nameEt,reasonEt;
    ImageView typeIv;
    TextView typeTv,timeTv;
    GridView typeGv;
    Button saveBtn;
    List<TypeBean> typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean;   //将需要插入到记账本当中的数据保存成对象的形式

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean();   //创建对象
        accountBean.setTypename("朋友");
        accountBean.setsImageId(R.mipmap.ic_pengyou_fs);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        setInitTime();
        //给GridView填充数据的方法
        loadDataToGV();
        setGVListener(); //设置GridView每一项的点击事件
        return view;
    }
    /* 获取当前时间，显示在timeTv上*/
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    /* 设置GridView每一项的点击事件*/
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated();  //提示绘制发生变化了
                TypeBean typeBean = typeList.get(position);
                String typename = typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);
                int simageId = typeBean.getSimageId();
                typeIv.setImageResource(simageId);
                accountBean.setsImageId(simageId);
            }
        });
    }

    /* 给GridView填出数据的方法*/
    public void loadDataToGV() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
    }

    private void initView(View view) {
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        nameEt = view.findViewById(R.id.frag_record_et_name);
        reasonEt = view.findViewById(R.id.frag_record_et_reason);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        saveBtn = view.findViewById(R.id.saveData);
        timeTv.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        //设置金额文本框焦点，光标文字
        moneyEt.setFocusable(true);
        moneyEt.requestFocus();
        moneyEt.setFocusableInTouchMode(true);
        //设置金额框监听事件
        moneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //文本改变前
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //文本改变时
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本改变后，一般使用此方法
                //获取输入钱数
                String moneyStr = moneyEt.getText().toString();
                if (TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")) {
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
            }
        });

        //设置姓名文本框焦点，光标文字
        nameEt.setFocusable(true);
        nameEt.requestFocus();
        nameEt.setFocusableInTouchMode(true);
        //设置姓名框监听事件
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //文本改变前
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //文本改变时
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本改变后，一般使用此方法
                //获取输入姓名
                String nameStr = nameEt.getText().toString();
                if (TextUtils.isEmpty(nameStr)) {
                    getActivity().finish();
                    return;
                }
                accountBean.setname(nameStr);
            }
        });

        //设置姓名文本框焦点，光标文字
        reasonEt.setFocusable(true);
        reasonEt.requestFocus();
        reasonEt.setFocusableInTouchMode(true);
        //设置姓名框监听事件
        reasonEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //文本改变前
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //文本改变时
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本改变后，一般使用此方法
                //获取输入姓名
                String reasonStr = reasonEt.getText().toString();
                if (TextUtils.isEmpty(reasonStr)) {
                    getActivity().finish();
                    return;
                }
                accountBean.setreason(reasonStr);
            }
        });
    }

    /* 让子类一定要重写这个方法*/
    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case R.id.saveData:
                if(accountBean.getMoney() == 0){
                    Toast.makeText(getActivity(),"金额不可为0",Toast.LENGTH_SHORT).show();
                    // 返回上一级页面
                    getActivity().finish();
                }
                //获取记录的信息，保存在数据库当中
                saveAccountToDB();

                // 返回上一级页面
                getActivity().finish();
                break;
        }
    }
    /* 弹出显示时间的对话框*/
   private void showTimeDialog() {
        SelectTimeDialog dialog = new SelectTimeDialog(getContext());
        dialog.show();
        //设定确定按钮被点击了的监听器
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }

}
