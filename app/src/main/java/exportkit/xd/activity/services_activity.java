
	 
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


import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.adapter.ServiceListAdapter;
import exportkit.xd.model.dichvu;
import exportkit.xd.model.dog;

	public class services_activity extends Activity {

	private View buydog;
	private View profile;
	private ServiceListAdapter serviceListAdapter;
	private RecyclerView listser;
	private DatabaseReference serDatabase;
	private ArrayList<dichvu> dichvus;
	private SearchView searchView;
	private static final int REQUEST_CODE_DETAIL = 1;

		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.services);

		buydog=findViewById(R.id.rectangle_2);
		profile=findViewById(R.id.rectangle_2_ek2);
		listser=findViewById(R.id.list_ser);
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

		serDatabase= FirebaseDatabase.getInstance().getReference().child("dichvu");
		dichvus = new ArrayList<>();

		listser.setLayoutManager(new LinearLayoutManager(this));
		serviceListAdapter = new ServiceListAdapter(dichvus);

		listser.setAdapter(serviceListAdapter);

		serDatabase.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				dichvus.clear();
				for (DataSnapshot snapshot : dataSnapshot.getChildren()){
					dichvu dichvu = snapshot.getValue(dichvu.class);
					dichvus.add(dichvu);
				}
				serviceListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

		buydog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(services_activity.this , buydogs_activity.class));
				finish();
			}
		});

		profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(services_activity.this , profile_activity.class));
				finish();
			}
		});

		serviceListAdapter.onBind = (viewHolder, position) -> {
			viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent=new Intent(services_activity.this, service_detail_activity.class);
					String data;
					AsyncTask.execute(new Runnable() {
						@Override
						public void run() {
							@SuppressLint("ResourceType") String data = String.valueOf(serviceListAdapter.getId(position));
							intent.putExtra("EXTRA_DATA_INPUT", data);
							startActivityForResult(intent,REQUEST_CODE_DETAIL);
						}
					});
				}
			});
		};
	
	}

	private void filter(String text) {
		ArrayList<dichvu> filteredlist = new ArrayList<>();
		for (dichvu item : dichvus) {
			if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
				filteredlist.add(item);
			}
		}
		if (filteredlist.isEmpty()) {
			Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
		} else {
			serviceListAdapter.updateList(filteredlist);
		}
	}
}
	
	