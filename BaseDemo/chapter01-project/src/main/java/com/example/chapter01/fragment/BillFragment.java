package com.example.chapter01.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.chapter01.R;
import com.example.chapter01.adapter.BillListAdapter;
import com.example.chapter01.adapter.BillPageAdapter;
import com.example.chapter01.database.BillDBHelper;
import com.example.chapter01.enity.BillInfo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {


    public static BillFragment newInstance(String yearMonth) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString("yearMonth",yearMonth);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ListView lv_bill = view.findViewById(R.id.lv_bill);
        // 获取数据帮助器，查询指定月份的账单记录
        BillDBHelper dbHelper = BillDBHelper.getInstance(getContext());
        // 获得需要查询的条件
        Bundle arguments = getArguments();
        String yearMonth = arguments.getString("yearMonth");
        // 根据传入的条件，查询指定的账单记录
        List<BillInfo> billInfoList = dbHelper.queryByMonth(yearMonth);
        // 设置适配器、设置适配器
        BillListAdapter billListAdapter = new BillListAdapter(getContext(), billInfoList);
        lv_bill.setAdapter(billListAdapter);

        return view;
    }
}