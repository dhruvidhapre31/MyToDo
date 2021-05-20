package com.example.mytodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.viewHolder>{

    ArrayList<model> model_list;
    Context context;

    public recyclerAdapter(ArrayList<model> model_list, Context context){
        this.model_list = model_list;
        this.context = context;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public TextView task_name, due_date;
        public CheckBox taskCB;

        public viewHolder(View view){
            super(view);
            task_name = view.findViewById(R.id.task_text_view);
            due_date = view.findViewById(R.id.date_text_view);
            taskCB = view.findViewById(R.id.task_check_box);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_task_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.task_name.setText(String.valueOf(model_list.get(position).getTask()));
        holder.due_date.setText(String.valueOf(model_list.get(position).getDueDate()));
    }

    @Override
    public int getItemCount() {
        return model_list.size();
    }

}
