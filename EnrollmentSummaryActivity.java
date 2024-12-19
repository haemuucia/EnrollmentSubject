package com.example.enrollment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class EnrollmentSummaryActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        TextView subjectList = findViewById(R.id.subject);
        TextView totalCredits = findViewById(R.id.totalcrd);

        db.collection("students").document(auth.getCurrentUser().getUid()).get()
                //show total subject selected & credits
                .addOnSuccessListener(document -> {
                    List<Map<String, Object>> subjects = (List<Map<String, Object>>) document.get("enrolledSubjects");
                    int credits = document.getLong("totalCredits").intValue();

                    StringBuilder subjectsText = new StringBuilder();
                    for (Map<String, Object> subject : subjects) {
                        subjectsText.append(subject.get("subjectName")).append("\n");
                    }

                    subjectList.setText(subjectsText.toString());
                    totalCredits.setText("Total Credits: " + credits);
                });
    }

}