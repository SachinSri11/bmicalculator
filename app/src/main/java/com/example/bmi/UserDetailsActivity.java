package com.example.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsActivity extends AppCompatActivity {
    private EditText nameEditText, dobEditText, heightEditText, weightEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        nameEditText = findViewById(R.id.name);
        dobEditText = findViewById(R.id.dob);
        heightEditText = findViewById(R.id.height);
        weightEditText = findViewById(R.id.weight);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> saveDetails());
    }

    private void saveDetails() {
        String name = nameEditText.getText().toString();
        String dob = dobEditText.getText().toString();
        String height = heightEditText.getText().toString();
        String weight = weightEditText.getText().toString();

        if (isValidInput(name, dob, height, weight)) {
            saveToPreferences(name, dob, height, weight);
            saveWeightHistory(weight);

            // Navigate to home screen
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput(String name, String dob, String height, String weight) {
        try {
            Float.parseFloat(height);
            Float.parseFloat(weight);
            return !name.isEmpty() && !dob.isEmpty();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void saveToPreferences(String name, String dob, String height, String weight) {
        SharedPreferences preferences = getSharedPreferences("user_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.putString("dob", dob);
        editor.putString("height", height);
        editor.putString("weight", weight);
        editor.apply();
    }

    private void saveWeightHistory(String weight) {
        SharedPreferences preferences = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("weight_history", "");
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> weightHistory = gson.fromJson(json, type);

        if (weightHistory == null) {
            weightHistory = new ArrayList<>();
        }

        weightHistory.add(weight);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("weight_history", gson.toJson(weightHistory));
        editor.apply();
    }
}
