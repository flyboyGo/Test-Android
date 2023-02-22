package com.example.room.base.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.base.enity.Student;

import java.util.List;

public class StudentRecycleViewAdapter extends RecyclerView.Adapter<StudentRecycleViewAdapter.MyViewHolder> {

    private List<Student> studentList;
    private Context context;

    public StudentRecycleViewAdapter(List<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tv_id.setText(String.valueOf(student.getId()));
        holder.tv_name.setText(student.getName());
        holder.tv_age.setText(String.valueOf(student.getAge()));
    }

    @Override
    public int getItemCount() {
        Log.d("test","size = " + studentList.size());
        return studentList.size();
    }


    protected class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_id;
        TextView tv_name;
        TextView tv_age;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_id = itemView.findViewById(R.id.id);
            this.tv_name = itemView.findViewById(R.id.name);
            this.tv_age = itemView.findViewById(R.id.age);
        }
    }
}
