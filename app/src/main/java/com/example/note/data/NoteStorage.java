package com.example.note.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NoteStorage {
    private static final String PREF_NAME = "notes_app";
    private static final String KEY_NOTES = "notes";

    public static void saveNotes(Context context, List<Note> notes) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        editor.putString(KEY_NOTES, gson.toJson(notes));
        editor.apply();
    }

    public static List<Note> loadNotes(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_NOTES, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<Note>>(){}.getType();
        return new Gson().fromJson(json, type);
    }
}
