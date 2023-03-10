package com.example.notes_shunyakov;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Notes_List_Adapter extends  RecyclerView.Adapter<Notes_View_Holder>{

    Context context;
    List<Notes> list;

    NoteClickListener Listener;

    public Notes_List_Adapter(Context context, List<Notes> list, NoteClickListener listener) {
        this.context = context;
        this.list = list;
        Listener = listener;
    }

    @NonNull
    @Override
    public Notes_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Notes_View_Holder(LayoutInflater.from(context).inflate(R.layout.notes_list,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull Notes_View_Holder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.title.setSelected(true);

        holder.notes.setText(list.get(position).getNotes());

        holder.data.setText(list.get(position).getData());
        holder.data.setSelected(true);

        if(list.get(position).isPinned()){
            holder.pin.setImageResource(R.drawable.pin);
        }else {
            holder.pin.setImageResource(0);
        }
        int color_list = rndcolor();
        holder.notes_form.setCardBackgroundColor(holder.itemView.getResources().getColor(color_list,null));

        holder.notes_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_form.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Listener.onLongClick(list.get(holder.getAdapterPosition()),holder.notes_form);
                return true;
            }
        });

        }
        private int rndcolor(){
        List<Integer> color = new ArrayList<>();
        color.add(R.color.rnd);
        color.add(R.color.rnd1);
        color.add(R.color.rnd2);
        color.add(R.color.rnd3);
        color.add(R.color.rnd4);
        color.add(R.color.rnd5);
        color.add(R.color.rnd6);
        color.add(R.color.rnd7);
        color.add(R.color.rnd8);
            Random random = new Random();
            int randomcolor = random.nextInt(color.size());
            return  color.get(randomcolor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}
class Notes_View_Holder extends RecyclerView.ViewHolder {
    CardView notes_form;
    TextView title;
    TextView notes;
    ImageView pin;
    TextView data;

    public Notes_View_Holder(@NonNull View itemView) {
        super(itemView);
        notes_form = itemView.findViewById(R.id.notes_form);
        title = itemView.findViewById(R.id.title);
        notes = itemView.findViewById(R.id.notes);
        pin = itemView.findViewById(R.id.pin);
        data = itemView.findViewById(R.id.data);
    }
}
