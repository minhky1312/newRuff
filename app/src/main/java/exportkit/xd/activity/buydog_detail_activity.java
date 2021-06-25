
	 
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
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.model.User;
import exportkit.xd.model.dog;

public class buydog_detail_activity extends Activity {


	public static final String EXTRA_DATA = "EXTRA_DATA_INPUT";
	private RelativeLayout back;
	private DatabaseReference mDatabase;
	private ArrayList<dog> dogs;
	private dog dog1;
	private TextView title;
	private TextView mota;
	private TextView age;
	private TextView diachi;
	private TextView gia;
	private TextView gioitinh;
	private TextView cannang;
	private TextView loai;
	private TextView sdt;
	private FirebaseUser user;
	private User u;
	private ImageView hinhanh;
	private FloatingActionButton del;
	private FloatingActionButton edit;
	private TextView status;
	private RelativeLayout call;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buydog_detail);
		final int input = Integer.parseInt(getIntent().getStringExtra(EXTRA_DATA));

		age=findViewById(R.id.age1);
		diachi=findViewById(R.id.diachi1);
		title=findViewById(R.id.title1);
		mota=findViewById(R.id.detail1);
		gia=findViewById(R.id.gia1);
		hinhanh=findViewById(R.id.rectangle_15);
		gioitinh=findViewById(R.id.gioitinh1);
		cannang=findViewById(R.id.cannang1);
		loai=findViewById(R.id.loai1);
		sdt=findViewById(R.id.sdt1);
		del=findViewById(R.id.imageButton);
		edit=findViewById(R.id.imageButton1);
		status=findViewById(R.id.status);
		call=findViewById(R.id.frame_26);

		user = FirebaseAuth.getInstance().getCurrentUser();
		mDatabase= FirebaseDatabase.getInstance().getReference();
		dogs = new ArrayList<>();

		call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (status.getText().toString().equals("Liên hệ")){
					String phone = (sdt.getText().toString().split(","))[1].trim();
					if (phone.length() > 0) {
						Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
						startActivity(intent);

					}
				}
			}
		});

		edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(buydog_detail_activity.this , buydog_edititem_activity.class);
				intent.putExtra("iddog",input);
				startActivity(intent);

			}
		});

		del.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(buydog_detail_activity.this).setIcon(android.R.drawable.ic_delete)
						.setTitle("Delete post").setMessage("Are you sure you want to delete this post?")
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mDatabase.child("dog").child("dog"+String.valueOf(input+1)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
									@Override
									public void onComplete(@NonNull Task<Void> task) {
										if (task.isSuccessful()){
											Toast.makeText(buydog_detail_activity.this, "Delete Successfully!", Toast.LENGTH_SHORT).show();
										}
										else Toast.makeText(buydog_detail_activity.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
									}
								});
							}
						}).setNegativeButton("No", null).show();
			}
		});

		mDatabase.child("dog").child("dog"+String.valueOf(input+1)).
				get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DataSnapshot> task) {
				if (!task.isSuccessful()) {
					Toast.makeText(buydog_detail_activity.this, "Error getting data", Toast.LENGTH_SHORT).show();
				}
				else {
					dog1 = (dog) task.getResult().getValue(dog.class);
					title.setText(dog1.getTitle());
					mota.setText(dog1.getMota());
					age.setText(dog1.getAge());
					gioitinh.setText(dog1.getGioitinh());
					gia.setText(dog1.getGia()+" VNĐ");
					loai.setText(dog1.getLoai());
					cannang.setText(String.valueOf(dog1.getCan_nang()));
					diachi.setText(dog1.getDiachi());
					Picasso.get().load(Uri.parse(dog1.getHinhanh())).into(hinhanh);
					sdt.setText(dog1.getIdchu());
					if (dog1.getIdchu().equals(user.getUid())){
						if (dog1.isTrang_thai())
							status.setText("Chưa bán");
						else status.setText("Đã bán");
					}
					phone();
				}
			}
		});



		back=findViewById(R.id.frame_17_ek4);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	
	}

	private void phone(){
		mDatabase.child("User").child((String)sdt.getText()).
				get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DataSnapshot> task) {
				if (!task.isSuccessful()) {
					Toast.makeText(buydog_detail_activity.this, "Error getting data", Toast.LENGTH_SHORT).show();
				}
				else {
					u = (User) task.getResult().getValue(User.class);
					if (user.getUid().equals(u.getUid())){
						sdt.setText("Bởi tôi");
						del.setVisibility(View.VISIBLE);
						edit.setVisibility(View.VISIBLE);
					}
					else if (((String)u.getName()).length()>0&&((String)u.getPhone()).length()>0)
						sdt.setText("Bởi "+(String)u.getName()+", "+(String)u.getPhone());
					else if (((String)u.getPhone()).length()>0&&((String)u.getName()).length()==0)
						sdt.setText("Bởi "+(String)u.getEmail()+", "+(String)u.getPhone());
					else if (((String)u.getName()).length()==0&&((String)u.getPhone()).length()==0)
						sdt.setText("Bởi "+(String)u.getEmail());
				}
			}
		});
	}
}
	
	