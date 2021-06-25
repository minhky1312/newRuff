
	 
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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.adapter.DogListAdapter;
import exportkit.xd.model.dog;

	public class buydogs_activity extends Activity {

	private View serviecs;
	private View profile;
	private FloatingActionButton addBtn;
	private DogListAdapter dogListAdapter;
	private RecyclerView listdog;
	private FirebaseUser user;
	private DatabaseReference dogDatabae;
	private ArrayList<dog> dogs;
	private SearchView searchView;
	private static final int REQUEST_CODE_DETAIL = 1;
	private static final int REQUEST_CODE_ADD = 2;

		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.buydogs);

		user = FirebaseAuth.getInstance().getCurrentUser();
		serviecs=findViewById(R.id.rectangle_2_ek1);
		profile=findViewById(R.id.rectangle_2_ek2);
		addBtn=findViewById(R.id.addItem);
		listdog=findViewById(R.id.list_dog);
		searchView=findViewById(R.id.etSearch);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				filter(newText);
				return false;
			}
		});

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
					if (dog.isTrang_thai())
						dogs.add(dog);
				}
				dogListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

		serviecs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(buydogs_activity.this , services_activity.class));
				finish();
			}
		});

		profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(buydogs_activity.this , profile_activity.class));
				finish();
			}
		});

		addBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(buydogs_activity.this , buydog_additem_activity.class));
			}
		});

//		dogListAdapter.onBind = (viewHolder, position) -> {
//			viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					Intent intent=new Intent(buydogs_activity.this, buydog_detail_activity.class);
//					buydogs_activity.this.startActivityForResult(intent, 1002);
//				}
//			});
//		};

		dogListAdapter.onBind = (viewHolder, position) -> {
			viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent=new Intent(buydogs_activity.this, buydog_detail_activity.class);
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

		private void filter(String text) {
		ArrayList<dog> filteredlist = new ArrayList<>();
		for (dog item : dogs) {
			if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
				filteredlist.add(item);
			}
		}
		if (filteredlist.isEmpty()) {
			Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
		} else {
			dogListAdapter.updateList(filteredlist);
		}
	}
}
	
	