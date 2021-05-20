package com.example.mytodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth Auth;
    RecyclerView recyclerView;
    FloatingActionButton add;
    ArrayList<model> model_list;
    recyclerAdapter adapter;
    FirebaseDatabase database;
    LinearLayout noTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model_list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        add = findViewById(R.id.add_fab);
        noTask = findViewById(R.id.noTask);
        noTask.setVisibility(View.INVISIBLE);

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new recyclerAdapter(model_list, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTask.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database.getReference(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model_list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    model todo = dataSnapshot.getValue(model.class);
                    model_list.add(todo);
                }
                adapter.notifyDataSetChanged();

                if (model_list.isEmpty()){
                    noTask.setVisibility(View.VISIBLE);
                }
                else {
                    noTask.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Auth = FirebaseAuth.getInstance();
        switch (item.getItemId()){
            case R.id.log_out:
                Auth.signOut();
                startActivity(new Intent(MainActivity.this, LogIn.class));
        }
        return super.onOptionsItemSelected(item);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            model list = model_list.get(pos);

            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            database = FirebaseDatabase.getInstance();

            database.getReference().child(userID).child(list.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    model_list.remove(pos);
                    adapter.notifyItemRemoved(pos);
                    adapter.notifyDataSetChanged();

                    if(model_list.isEmpty())
                        noTask.setVisibility(View.VISIBLE);
                    else
                        noTask.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}