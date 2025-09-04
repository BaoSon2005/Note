package com.example.note;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.Adapter.NoteAdapter;
import com.example.note.data.Note;
import com.example.note.data.NoteStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<>();
    private TextView tvEmpty;
    private boolean isGrid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerNotes);
        tvEmpty = findViewById(R.id.tvEmpty);
        EditText searchBar = findViewById(R.id.searchBar);
        ImageButton btnToggleLayout = findViewById(R.id.btnToggleLayout);
        FloatingActionButton btnAdd = findViewById(R.id.btnAddNote);

        adapter = new NoteAdapter(this, noteList);
        recyclerView.setAdapter(adapter);
        setLayoutManager();

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NoteEditorActivity.class));
        });

        btnToggleLayout.setOnClickListener(v -> {
            isGrid = !isGrid;
            setLayoutManager();
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
                toggleEmpty();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> latestNotes = NoteStorage.loadNotes(this);
        adapter.setNotes(latestNotes);
        toggleEmpty();
    }

    private void setLayoutManager() {
        if (isGrid) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void toggleEmpty() {
        if (adapter.getItemCount() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
