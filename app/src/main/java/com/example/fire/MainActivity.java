package com.example.fire;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button button;
    int cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// 데이터베이스 쓰기
        button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("MainActivity", "Button - onClickListener");


                FirebaseDatabase.getInstance().getReference().push().setValue(new CafeItem("바닐라 라떼",3000,"url3"));

                cnt++;
            }
        });

        // 데이터베이스 읽기 #2. Single ValueEventListener
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("MainActivity", "Single ValueEventListener : " + snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       // 출처: https://stack07142.tistory.com/282 [Hello World]


    }
}
