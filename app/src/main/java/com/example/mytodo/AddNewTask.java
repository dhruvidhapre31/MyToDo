package com.example.mytodo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewTask extends Activity implements DatePickerDialog.OnDateSetListener {

    DatePickerDialog datePickerDialog;
    EditText taskET;
    TextView dueDateTV;
    Button saveBTN;
    ImageButton dateImgBtn;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        taskET = findViewById(R.id.new_task_edit_text);
        dueDateTV = findViewById(R.id.due_date_text_view);
        saveBTN = findViewById(R.id.save_button);
        dateImgBtn = findViewById(R.id.set_date_img_btn);

        getActionBar().hide();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        getWindow().setAttributes(params);

        dateImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFirebase();
            }
        });
    }

    void addToFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String key = firebaseDatabase.getReference(userID).push().getKey();
        String taskName = taskET.getText().toString();
        String duedate = dueDateTV.getText().toString();

        if (taskName.isEmpty()) {
            taskET.setError("Enter your task");
        }
        if (duedate.isEmpty()) {
            dueDateTV.setError("Add due date");
        }
        if ((!taskName.isEmpty()) && !(duedate.isEmpty())){
            model task = new model();
            task.setUserID(userID);
            task.setKey(key);
            task.setTask(taskName);
            task.setDueDate(duedate);

            Map<String, Object> updates = new HashMap<>();
            updates.put(key, task.toFirebaseObject());
            firebaseDatabase.getReference(userID).updateChildren(updates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        finish();
                    }
                }
            });
            finish();
        }
    }

    private void showDatePickerDialog(){
        datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet (DatePicker view,int year, int month, int dayOfMonth){
        month=month+1;
        String date = dayOfMonth +"/" + month +"/"+ year;
        dueDateTV.setText(date);
    }
}


