package net.coding4ever.roedhi.mynotesapp;

import net.coding4ever.roedhi.mynotesapp.entity.Note;

import java.util.ArrayList;

public interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<Note> notes);
}
