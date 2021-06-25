
	 
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
import android.net.Uri;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.model.User;
import exportkit.xd.model.dichvu;
import exportkit.xd.model.dog;

public class service_detail_activity extends Activity {

	public static final String EXTRA_DATA = "EXTRA_DATA_INPUT";
	private RelativeLayout back;
	private DatabaseReference mDatabase;
	private ArrayList<dichvu> dichvus;
	private TextView title;
	private TextView mota;
	private TextView gia;
	private ImageView hinhanh;
	private dichvu dichvu1;
	private RelativeLayout call;
	private TextView sdt;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_detail);
		final int input = Integer.parseInt(getIntent().getStringExtra(EXTRA_DATA));

		title=findViewById(R.id.title);
		mota=findViewById(R.id.detail);
		gia=findViewById(R.id.gia);
		hinhanh=findViewById(R.id.rectangle_15_ek1);
		back=findViewById(R.id.frame_17_ek4);
		call=findViewById(R.id.frame_26_ek1);
		sdt=findViewById(R.id.sdt);

		call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = sdt.getText().toString().trim();
				if (phone.length() > 0) {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
					startActivity(intent);
				}
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mDatabase= FirebaseDatabase.getInstance().getReference();
		dichvus = new ArrayList<>();

		mDatabase.child("dichvu").child("dichvu"+String.valueOf(input+1)).
				get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DataSnapshot> task) {
				if (!task.isSuccessful()) {
					Toast.makeText(service_detail_activity.this, "Error getting data", Toast.LENGTH_SHORT).show();
				}
				else {
					dichvu1 = (dichvu) task.getResult().getValue(dichvu.class);
					title.setText(dichvu1.getTitle());
					mota.setText(dichvu1.getMota());
					gia.setText(dichvu1.getGia()+" VNĐ");
					Picasso.get().load(Uri.parse(dichvu1.getHinhanh())).into(hinhanh);
				}
			}
		});
	}
}
	
	