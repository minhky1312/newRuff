package exportkit.xd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.adapter.DogListAdapter;
import exportkit.xd.model.User;
import exportkit.xd.model.dog;

public class editprofile_activity extends Activity {

    private RelativeLayout changepass;
    private RelativeLayout back;
    private EditText email;
    private EditText phone;
    private EditText nickname;
    private RelativeLayout buttonOk;
    private FirebaseUser user;
    private User u;
    private Task<DataSnapshot> dogDatabae;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        changepass=findViewById(R.id.button_ek1);
        back=findViewById(R.id.frame_17_ek4);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.editPhone);
        nickname=findViewById(R.id.nickname);
        buttonOk=findViewById(R.id.button);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        u=new User();

        user = FirebaseAuth.getInstance().getCurrentUser();
        email.setText(user.getEmail());
        nickname.setText((user.getDisplayName()));

        mDatabase.child("User").child(user.getUid()).
                get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(editprofile_activity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                }
                else {
                    u = (User) task.getResult().getValue(User.class);
                    phone.setText(u.getPhone());
                }
            }
        });



        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editprofile_activity.this , changepassword_activity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile(){
        String txt_email = email.getText().toString();
        String txt_phone = phone.getText().toString();
        String txt_nickname = nickname.getText().toString();


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(txt_nickname)
                .build();

        if (txt_nickname.length()>0){
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(editprofile_activity.this, "User profile updated.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (txt_email.length()>0){
            user.updateEmail(txt_email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(editprofile_activity.this, "User email address updated.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        if (txt_phone.length()>0){
            mDatabase.child("User").child(user.getUid()).child("phone").setValue(txt_phone);
            Toast.makeText(editprofile_activity.this, "User phone number updated.", Toast.LENGTH_SHORT).show();
        }
    }
}