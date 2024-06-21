package com.example.bmi;// HomeActivity.java
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private TextView heightTextView, weightTextView, bmiTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        heightTextView = findViewById(R.id.height_text_view);
        weightTextView = findViewById(R.id.weight_text_view);
        bmiTextView = findViewById(R.id.bmi_text_view);

        loadUserDetails();
    }

    private void loadUserDetails() {
        SharedPreferences preferences = getSharedPreferences("user_details", MODE_PRIVATE);
        String height = preferences.getString("height",null);
        String weight = preferences.getString("weight",null);

        heightTextView.setText("Height: " + height);
        weightTextView.setText("Weight: " + weight);

        // Calculate and display BMI
        try {
            float heightValue = Float.parseFloat(height);
            float weightValue = Float.parseFloat(weight);
            float bmi = calculateBMI(heightValue, weightValue);
            bmiTextView.setText("BMI: " + bmi);
        } catch (NumberFormatException e) {
            bmiTextView.setText("BMI: N/A");
        }
    }

    private float calculateBMI(float height, float weight) {
        // Convert height to meters if needed and calculate BMI
        if (height > 10) { // assuming height in cm if greater than 10
            height = height / 100;
        }
        return weight / (height * height);
    }

    public void onUpdateDetailsClick(View view) {
        Intent intent = new Intent(this, UpdateDetailsActivity.class);
        startActivity(intent);
    }

    public void onViewWeightHistoryClick(View view) {
        Intent intent = new Intent(this, WeightHistoryActivity.class);
        startActivity(intent);
    }
}
