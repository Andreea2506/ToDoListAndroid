package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private java.util.List<ToDo> List;
    private MainActivity activity;
    private DatabaseHelper myDB;

    public Adapter(DatabaseHelper myDB , MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_layout , parent , false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDo item = List.get(position);
        holder.myCheckBox.setText(item.getTask());
        holder.myCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.myCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myDB.updateStatus(item.getId() , 1);
                }else
                    myDB.updateStatus(item.getId() , 0);
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ToDo> mList){
        this.List = mList;
        notifyDataSetChanged();
    }

    public void deletTask(int position){
        ToDo item = List.get(position);
        myDB.deleteTask(item.getId());
        List.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDo item = List.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getTask());

        AddToDo task = new AddToDo();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager() , task.getTag());


    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox myCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myCheckBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
