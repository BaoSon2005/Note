package com.example.note.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.NoteEditorActivity;
import com.example.note.R;
import com.example.note.data.Note;
import com.example.note.data.NoteStorage;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Context context;
    private List<Note> noteList;
    private List<Note> filteredList;

    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
        this.filteredList = new ArrayList<>(noteList);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = filteredList.get(position);

        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());
        holder.tvTime.setText("Cập nhật: " + note.getUpdatedAt());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteEditorActivity.class);
            intent.putExtra("noteId", note.getId());
            context.startActivity(intent);
        });


        holder.btnDelete.setOnClickListener(v -> {
            noteList.remove(note);
            filteredList.remove(note);
            NoteStorage.saveNotes(context, noteList);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void setNotes(List<Note> notes) {
        this.noteList = notes;
        this.filteredList = new ArrayList<>(notes);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(noteList);
        } else {
            for (Note n : noteList) {
                if (n.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        n.getContent().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(n);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvTime;
        ImageView btnDelete;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
