
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		home
	 *	@date 		1624354588381
	 *	@title 		Page 2
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package exportkit.xd.activity;

import android.app.Activity;
import android.os.Bundle;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import exportkit.xd.R;
import exportkit.xd.model.User;

	public class register_activity extends Activity {

	private EditText email;
	private EditText password;
	private EditText rePassword;
	private RelativeLayout register;
	private TextView login;
	private FirebaseUser user;
	private FirebaseAuth auth;
	private DatabaseReference mDatabase;


	private RelativeLayout back;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		email=findViewById(R.id.editTextTextEmailAddress3);
		password=findViewById(R.id.editTextTextPassword3);
		rePassword=findViewById(R.id.editTextTextPassword4);
		register=findViewById(R.id.button_ek3);
		login=findViewById(R.id._log_in_);
		back=findViewById(R.id.frame_17_ek4);
		user = FirebaseAuth.getInstance().getCurrentUser();

		auth= FirebaseAuth.getInstance();

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(register_activity.this , home_activity.class));
				finish();
			}
		});

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String txt_email=email.getText().toString();
				String txt_password=password.getText().toString();
				String txt_repassword=rePassword.getText().toString();

				if (TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
					Toast.makeText(register_activity.this,"Empty credentails!", Toast.LENGTH_SHORT).show();
				} else if (txt_password.length()<6){
					Toast.makeText(register_activity.this,"Password too short",Toast.LENGTH_SHORT).show();
				}
				else if (!txt_password.equals(txt_repassword)){
					Toast.makeText(register_activity.this,"Re-enter Password Incorrect",Toast.LENGTH_SHORT).show();
				}
				else {
					registerUser(txt_email,txt_password);
				}
			}
		});

	}

		private void registerUser(String email, String password) {

			auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(register_activity.this,new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()){
//						Toast.makeText(register_activity.this,"Registering user successful!",Toast.LENGTH_SHORT).show();
//						startActivity(new Intent(register_activity.this,buydogs_activity.class));
//						finish();

						auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
							@Override
							public void onComplete(@NonNull Task<Void> task) {
								if (task.isSuccessful()) {
									User NewUser = new User(user.getUid(),email,"","");
									mDatabase.child("User").child(user.getUid()).setValue(NewUser);

									Toast.makeText(register_activity.this, "Please check email for verification.", Toast.LENGTH_SHORT).show();
									FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(register_activity.this, home_activity.class));
                                    finish();
								}else{
									Toast.makeText(register_activity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
								}
							}
						});
					} else {
						Toast.makeText(register_activity.this,"Registration failed!",Toast.LENGTH_SHORT).show();
					}
				}
			});

		}
}
	
	