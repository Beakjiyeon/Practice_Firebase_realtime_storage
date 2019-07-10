package com.example.fire;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;
// 이미지 업로드 https://www.youtube.com/watch?v=6u0gzjth4IE
public class MainActivity extends AppCompatActivity {

    public Uri imguri;
    Button ch,up;
    ImageView img;
    StorageReference mStorageRef;
    private StorageTask uploadTask; //중복 방지
    Button button;
    int cnt=0;

    // 앨범에서 파일 고르기
    private void fileChooser(){
        Intent intent=new Intent();
        intent.setType("image/'");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }


    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    // 이미지를 파이어베이스에 올림
    private void fileUploader(){
        StorageReference Ref=mStorageRef.child(System.currentTimeMillis()+","+getExtension(imguri));
        uploadTask=Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        // 성공하면
                        Toast.makeText(MainActivity.this,"Image Uproad Successfultty",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imguri=data.getData();
            img.setImageURI(imguri);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // storage
        mStorageRef= FirebaseStorage.getInstance().getReference("Image");
        ch=(Button)findViewById(R.id.choosebtn);
        up=(Button)findViewById(R.id.uploadbtn);
        img=(ImageView)findViewById(R.id.imageview);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              fileChooser();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask!=null&&uploadTask.isInProgress()){
                    // 파일이 올라가는 중임을 암시
                    Toast.makeText(MainActivity.this,"upload in progress",Toast.LENGTH_LONG).show();
                }else{
                    fileUploader();
                }
            }
        });


        // 데이터베이스 쓰기
        button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Button - onClickListener");
                FirebaseDatabase.getInstance().getReference().push().setValue(new CafeItem("바닐라 라떼",3000,"url3"));

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


       // 파이어베이스 입출력 : 출처: https://stack07142.tistory.com/282 [Hello World]

    }
}
