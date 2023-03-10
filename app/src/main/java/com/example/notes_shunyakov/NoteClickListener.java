package com.example.notes_shunyakov;

import androidx.cardview.widget.CardView;

public interface NoteClickListener {


    void  onClick(Notes notes);
    void  onLongClick(Notes notes, CardView cardView);
}
