
	 
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;


import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.adapter.DogListAdapter;
import exportkit.xd.model.Upload;
import exportkit.xd.model.dog;

	public class profile_activity extends Activity {
	private static final int PICK_IMAGE_REQUEST = 1;
	private static final int REQUEST_CODE_DETAIL = 1;
	private View editprofile;
	private View buydog;
	private View serviecs;
	private RelativeLayout logout;
	private TextView username;
	private ImageView img;
	private Uri imageuri;
	private StorageReference mStorageref;
	private DatabaseReference mDatabaseref;
	private FirebaseUser user;
	private DogListAdapter dogListAdapter;
	private RecyclerView listdog;
	private DatabaseReference dogDatabae;
	private ArrayList<dog> dogs;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);


		editprofile =findViewById(R.id._rectangle_25);
		serviecs=findViewById(R.id.rectangle_2_ek1);
		buydog=findViewById(R.id.rectangle_2);
		logout=findViewById(R.id.frame_36);
		username=findViewById(R.id.username);
		img=findViewById(R.id.ellipse_1);

		listdog=findViewById(R.id.list_dog);

		user = FirebaseAuth.getInstance().getCurrentUser();

		dogDatabae= FirebaseDatabase.getInstance().getReference().child("dog");
		dogs = new ArrayList<>();

		listdog.setLayoutManager(new GridLayoutManager(this, 2));
		dogListAdapter = new DogListAdapter(dogs);

		listdog.setAdapter(dogListAdapter);

		dogDatabae.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				dogs.clear();
				for (DataSnapshot snapshot : dataSnapshot.getChildren()){
					dog dog = snapshot.getValue(dog.class);
					if (dog.getIdchu().equals(user.getUid())){
						dogs.add(dog);
					}
				}
				dogListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

		mStorageref = FirebaseStorage.getInstance().getReference("Upload Photos");
		mDatabaseref = FirebaseDatabase.getInstance().getReference("Upload Photos");

		if (user.getDisplayName().equals(""))username.setText(user.getEmail());
		else username.setText(user.getDisplayName());
		if (user.getPhotoUrl().toString().length()>0) {
			Picasso.get().load(user.getPhotoUrl()).into(img);
		}

		editprofile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(profile_activity.this , editprofile_activity.class));
			}
		});

		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openfilechooser();
			}
		});

		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FirebaseAuth.getInstance().signOut();
				Toast.makeText(profile_activity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(profile_activity.this , home_activity.class));
				finish();
			}
		});

		serviecs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(profile_activity.this , services_activity.class));
				finish();
			}
		});

		buydog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(profile_activity.this , buydogs_activity.class));
				finish();
			}
		});
		dogListAdapter.onBind = (viewHolder, position) -> {
			viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent=new Intent(profile_activity.this, buydog_detail_activity.class);
					String data;
					AsyncTask.execute(new Runnable() {
						@Override
						public void run() {
							@SuppressLint("ResourceType") String data = String.valueOf(dogListAdapter.getId(position));
							intent.putExtra("EXTRA_DATA_INPUT", data);
							startActivityForResult(intent,REQUEST_CODE_DETAIL);
						}
					});
				}
			});
		};
	}
	private String getFileExtension(Uri uri){
		ContentResolver cr = getContentResolver();
		MimeTypeMap mime = MimeTypeMap.getSingleton();
		return mime.getExtensionFromMimeType(cr.getType(uri));
	}

	private void uploadfile() {
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Uploading");
		progressDialog.show();

		if (imageuri !=null){
			StorageReference  filereference  = mStorageref.child(System.currentTimeMillis()+
					"."+getFileExtension(imageuri));

			filereference.putFile(imageuri)
					.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
							Toast.makeText(profile_activity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
							taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
								@Override
								public void onSuccess(Uri uri) {
									Uri downloadUrl = uri;
									Upload upload = new Upload(user.getEmail().trim(),downloadUrl.toString());
									progressDialog.show();
									String  uploadId = mDatabaseref.push().getKey();
									mDatabaseref.child(uploadId).setValue(upload);
									progressDialog.setCanceledOnTouchOutside(false);
									progressDialog.dismiss();
									uploadimg(uri);
								}
							});
						}
					})
					.addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception e) {

						}
					})
					.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
							double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
							progressDialog.setCanceledOnTouchOutside(false);
							progressDialog.setMessage("Uploaded  " +(int)progress+"%");
						}
					});

		}else
			Toast.makeText(this, "Please Select a Image", Toast.LENGTH_SHORT).show();
	}

	private void uploadimg(Uri uri){

		UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
				.setPhotoUri(uri)
				.build();

		user.updateProfile(profileUpdates)
				.addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							Toast.makeText(profile_activity.this, "Update photo profile successfully! ", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	private void openfilechooser() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent,PICK_IMAGE_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null &&
				data.getData()!=null){
			imageuri = data.getData();
			Picasso.get().load(imageuri).into(img);
			uploadfile();

		}
	}

}
	
	