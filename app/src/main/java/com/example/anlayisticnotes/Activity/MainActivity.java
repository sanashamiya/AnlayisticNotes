package com.example.anlayisticnotes.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anlayisticnotes.Adaptar.CategoryAdapter;
import com.example.anlayisticnotes.R;
import com.example.anlayisticnotes.model.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAnalytics mFirebaseAnalytics;

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

    ArrayList<Category> items;
    CategoryAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;
    TextView textView;
    Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btn = findViewById(R.id.Titelbtn);
        rv = findViewById(R.id.RvCategory);

        items = new ArrayList<Category>();
        adapter = new CategoryAdapter(this, items);
        GetAllCategory();

        screenTrack("HomeScreen");

    }

    private void GetAllCategory() {
        db.collection("address").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("ssss", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String note = documentSnapshot.getString("name");
                                    Category notes = new Category(id, note);
                                    items.add(notes);

                                    rv.setLayoutManager(layoutManager);
                                    rv.setHasFixedSize(true);
                                    rv.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    Log.e("ssss", items.toString());
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e("LogDATA", "get failed with ");


                    }
                });
    }

    public void screenTrack(String screenName) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);//save it at firebase
    }

    protected void onPause() {
        Calendar calendar = Calendar.getInstance();
        int hour2 = calendar.get(Calendar.HOUR);
        int minute2 = calendar.get(Calendar.MINUTE);
        int second2 = calendar.get(Calendar.SECOND);

        int h = hour2 - hour;
        int m = minute2 - minute;
        int s = second2 - second;

        HashMap<String, Object> users = new HashMap<>();
        users.put("name", "HomeScreen");
        users.put("hours", h);
        users.put("minute", m);
        users.put("seconds", s);
        db.collection("track")
                .add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("ssss", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ssss", "Error adding document", e);
                    }
                });

        super.onPause();
    }
}