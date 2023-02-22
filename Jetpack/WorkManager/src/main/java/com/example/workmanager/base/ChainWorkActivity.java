package com.example.workmanager.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.workmanager.R;
import com.example.workmanager.base.work.AWork;
import com.example.workmanager.base.work.BWork;
import com.example.workmanager.base.work.CWork;
import com.example.workmanager.base.work.DWork;
import com.example.workmanager.base.work.EWork;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class ChainWorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain_work);
    }

    @SuppressLint("EnqueueWork")
    public void addChainWork(View view) {

        OneTimeWorkRequest workA = new OneTimeWorkRequest.Builder(AWork.class).build();
        OneTimeWorkRequest workB = new OneTimeWorkRequest.Builder(BWork.class).build();
        OneTimeWorkRequest workC = new OneTimeWorkRequest.Builder(CWork.class).build();
        OneTimeWorkRequest workD = new OneTimeWorkRequest.Builder(DWork.class).build();
        OneTimeWorkRequest workE = new OneTimeWorkRequest.Builder(EWork.class).build();


        // 任务链
//        WorkManager.getInstance(this)
//                .beginWith(workA)
//                .then(workB)
//                .enqueue();

        // 任务链组合
        WorkContinuation workContinuation = WorkManager.getInstance(this)
                .beginWith(workA)
                .then(workB);

        WorkContinuation workContinuation2 = WorkManager.getInstance(this)
                .beginWith(workC)
                .then(workD);

        List<WorkContinuation> taskList = new ArrayList<>();
        taskList.add(workContinuation);
        taskList.add(workContinuation2);

        WorkContinuation.combine(taskList)
                .then(workE)
                .enqueue();
    }
}