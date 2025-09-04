package com.example.note;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.note.data.Note;
import com.example.note.data.NoteStorage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class NoteEditorActivity extends AppCompatActivity {
    private EditText etTitle, etContent;
    private Button btnSave, btnDelete;
    private String noteId = null;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        notes = NoteStorage.loadNotes(this);
        noteId = getIntent().getStringExtra("noteId");

        if (noteId != null) {
            Note note = findNoteById(noteId);
            if (note != null) {
                etTitle.setText(note.getTitle());
                etContent.setText(note.getContent());
            }
        } else {
            btnDelete.setVisibility(Button.GONE);
        }

        btnSave.setOnClickListener(v -> saveNote());
        btnDelete.setOnClickListener(v -> deleteNote());
    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, "Ghi chú trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        String now = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        if (noteId == null) {
            Note newNote = new Note(
                    UUID.randomUUID().toString(),
                    title,
                    content,
                    now,
                    now
            );
            notes.add(0, newNote);
        } else {
            // Cập nhật
            Note existingNote = findNoteById(noteId);
            if (existingNote != null) {
                existingNote.setTitle(title);
                existingNote.setContent(content);
                existingNote.setUpdatedAt(now);
            }
        }

        NoteStorage.saveNotes(this, notes);
        Toast.makeText(this, "Đã lưu ghi chú", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteNote() {
        if (noteId != null) {
            Note note = findNoteById(noteId);
            if (note != null) {
                notes.remove(note);
                NoteStorage.saveNotes(this, notes);
                Toast.makeText(this, "Đã xóa ghi chú", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    private Note findNoteById(String id) {
        for (Note n : notes) {
            if (n.getId().equals(id)) return n;
        }
        return null;
    }
}
