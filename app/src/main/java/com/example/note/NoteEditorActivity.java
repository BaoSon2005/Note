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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class NoteEditorActivity extends AppCompatActivity {
    private EditText etTitle, etContent;
    private Button btnSave, btnDelete;
    private List<Note> noteList ;
    private String noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        // Load danh sách ghi chú
        noteList = NoteStorage.loadNotes(this);

        // Lấy dữ liệu từ Intent (nếu sửa ghi chú)
        Intent intent = getIntent();
        noteId = intent.getStringExtra("noteId");
        if (noteId != null) {
            for (Note n : noteList) {
                if (n.getId().equals(noteId)) {
                    etTitle.setText(n.getTitle());
                    etContent.setText(n.getContent());
                    break;
                }
            }
            btnDelete.setEnabled(true);
        } else {
            btnDelete.setEnabled(false); // ghi chú mới thì không có nút xóa
        }

        // Nút Lưu
        btnSave.setOnClickListener(v -> saveNote());

        // Nút Xóa
        btnDelete.setOnClickListener(v -> deleteNote());
    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();
            return;
        }

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        if (noteId == null) {
            // thêm mới
            String id = UUID.randomUUID().toString();
            Note newNote = new Note(id, title, content, now, now);
            noteList.add(0, newNote);
        } else {
            // cập nhật
            for (Note n : noteList) {
                if (n.getId().equals(noteId)) {
                    n.setTitle(title);
                    n.setContent(content);
                    n.setUpdatedAt(now);
                    break;
                }
            }
        }

        NoteStorage.saveNotes(this, noteList);
        Toast.makeText(this, "Đã lưu ghi chú", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteNote() {
        if (noteId != null) {
            for (int i = 0; i < noteList.size(); i++) {
                if (noteList.get(i).getId().equals(noteId)) {
                    noteList.remove(i);
                    break;
                }
            }
            NoteStorage.saveNotes(this, noteList);
            Toast.makeText(this, "Đã xóa ghi chú", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
