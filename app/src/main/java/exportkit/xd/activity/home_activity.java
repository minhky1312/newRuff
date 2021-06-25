
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		home
	 *	@date 		1624157446680
	 *	@title 		Page 2
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package exportkit.xd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import exportkit.xd.R;

    public class home_activity extends Activity {


	private EditText email;
	private EditText password;
	private RelativeLayout login;
	private TextView register;
	private TextView resetpass;

	private FirebaseAuth auth;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		email=findViewById(R.id.editTextTextEmailAddress);
		password=findViewById(R.id.editTextTextPassword);
		login=findViewById(R.id.button_ek2);
		register=findViewById(R.id._register_);
		resetpass=findViewById(R.id.reset_pass);

		auth=FirebaseAuth.getInstance();

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String txt_email = email.getText().toString();
				String txt_password = password.getText().toString();
				if (TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
					Toast.makeText(home_activity.this,"Empty credentails!", Toast.LENGTH_SHORT).show();
				} else if (txt_password.length()<6){
					Toast.makeText(home_activity.this,"Password too short",Toast.LENGTH_SHORT).show();
				} else {
					loginUser(txt_email,txt_password);
				}
			}
		});

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(home_activity.this, register_activity.class));
			}
		});

		resetpass.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(home_activity.this, reset_password_activity.class));
			}
		});
	
	}

	private void loginUser(String email, String password) {
		auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (task.isSuccessful()){
					if (auth.getCurrentUser().isEmailVerified()) {
						Toast.makeText(home_activity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(home_activity.this, buydogs_activity.class));
						finish();
					}
					else
					{
						FirebaseAuth.getInstance().signOut();
						Toast.makeText(home_activity.this, "Please verify your email first!", Toast.LENGTH_SHORT).show();
						Intent intent = getIntent();
						finish();
						startActivity(intent);
					}
				} else {
					Toast.makeText(home_activity.this,"Login Failed!!",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		if (user!=null){
			startActivity(new Intent(home_activity.this, buydogs_activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
			Toast.makeText(home_activity.this,"Welcome back "+user.getEmail(),Toast.LENGTH_SHORT).show();
			finish();
		}
	}
}
	
	