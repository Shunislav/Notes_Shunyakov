package com.example.notes_shunyakov;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Insert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    FloatingActionButton float_add;
    Notes_List_Adapter notes_list_adapter;
    RoomDB database;
    List <Notes> notes = new ArrayList<>();
    SearchView view_search;
    Notes selNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view_search = findViewById(R.id.view_search);
        recyclerView = findViewById(R.id.rec);
        float_add = findViewById(R.id.add_nots);
        database = RoomDB.getInstance(this);
        notes = database.request().getAll();



        updateRecyclre(notes);

        float_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this,NotesActivity.class);
                startActivityForResult(intent,101);
            }
        });
        view_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter (s);
                return true;
            }
        });

    }

    private void filter(String s) {
        List<Notes> filteredList = new ArrayList<>();
        for(Notes singleNote : notes){
            if(singleNote.getTitle().toLowerCase().contains(s.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(singleNote);
            }

        }
        notes_list_adapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("notes");
                database.request().insert(new_notes);
                notes.clear();
                notes.addAll(database.request().getAll());
                notes_list_adapter.notifyDataSetChanged();
            }
        }
        if(requestCode == 102){
            if(resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("notes");
                database.request().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                notes.clear();
                notes.addAll(database.request().getAll());
                notes_list_adapter.notifyDataSetChanged();
            }
            }
        }


    private void updateRecyclre(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        notes_list_adapter=new Notes_List_Adapter(Main.this,notes,noteClickListener);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notes_list_adapter);
    }
    private final NoteClickListener noteClickListener = new NoteClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(Main.this, NotesActivity.class);
            intent.putExtra("not", notes);
            startActivityForResult(intent, 102);

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selNote = new Notes();
            selNote = notes;
            showmenu(cardView);

        }
    };

    private void showmenu(CardView cardView) {
        PopupMenu showmenu = new PopupMenu(this,cardView);
        showmenu.setOnMenuItemClickListener(this);
        showmenu.inflate(R.menu.menulist);
        showmenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {


        switch (menuItem.getItemId()){
            case R.id.pine:
                if(selNote.isPinned()){
                    database.request().pin(selNote.getID(),false);
                    Toast.makeText(Main.this,"unpin",Toast.LENGTH_SHORT).show();
                } else {
                    database.request().pin(selNote.getID(),true);
                    Toast.makeText(Main.this,"pin",Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(database.request().getAll());
                notes_list_adapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.request().delete(selNote);
                notes.remove(selNote);
                notes_list_adapter.notifyDataSetChanged();
                Toast.makeText(Main.this,"Delete complete",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;

        }
    }
}