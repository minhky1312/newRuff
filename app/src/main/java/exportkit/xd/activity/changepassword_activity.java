
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		home
	 *	@date 		1624372132986
	 *	@title 		Page 3
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
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import exportkit.xd.R;

    public class changepassword_activity extends Activity {

	private RelativeLayout back;
	private RelativeLayout changepass;
	private EditText password;
	private EditText repassword;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword);

		password=findViewById(R.id.editTextTextPassword2);
		repassword=findViewById(R.id.editTextTextPassword5);
		back=findViewById(R.id.frame_17_ek2);
		changepass=findViewById(R.id.button_ek4);

		changepass.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String newPass=password.getText().toString();
				String newRePass=repassword.getText().toString();

				if (TextUtils.isEmpty(newPass)||TextUtils.isEmpty(newRePass)){
					Toast.makeText(changepassword_activity.this,"Empty credentails!", Toast.LENGTH_SHORT).show();
				} else if (newPass.length()<6){
					Toast.makeText(changepassword_activity.this,"Password too short",Toast.LENGTH_SHORT).show();
				}
				else if (!newPass.equals(newRePass)){
					Toast.makeText(changepassword_activity.this,"Re-enter Password Incorrect",Toast.LENGTH_SHORT).show();
				}
				else {
					changePassword(newPass);
				}
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void changePassword(String pass){
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		user.updatePassword(pass)
				.addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							Toast.makeText(changepassword_activity.this,"Change Password Successful!",Toast.LENGTH_SHORT).show();
							finish();
						}
						else {
							Toast.makeText(changepassword_activity.this,"Change Password Failed!",Toast.LENGTH_SHORT).show();
						}
					}
				});
	}
}
	
	