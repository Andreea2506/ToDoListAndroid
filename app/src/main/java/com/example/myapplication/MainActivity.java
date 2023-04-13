package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {

    private RecyclerView myRecyclerview;
    private FloatingActionButton myFloatingButton;
    private DatabaseHelper myDB;
    private List<ToDo> myList;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerview = findViewById(R.id.recyclerView);
        myFloatingButton = findViewById(R.id.floatingActionButton);
        myDB = new DatabaseHelper(MainActivity.this);
        myList = new ArrayList<>();
        adapter = new Adapter(myDB , MainActivity.this);

        myRecyclerview.setHasFixedSize(true);
        myRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerview.setAdapter(adapter);

        myList = myDB.getAllTasks();
        Collections.reverse(myList);
        adapter.setTasks(myList);

        myFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDo.newInstance().show(getSupportFragmentManager() , AddToDo.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(myRecyclerview);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        myList = myDB.getAllTasks();
        Collections.reverse(myList);
        adapter.setTasks(myList);
        adapter.notifyDataSetChanged();
    }
}