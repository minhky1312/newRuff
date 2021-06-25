package exportkit.xd.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import exportkit.xd.R;
import exportkit.xd.model.Upload;
import exportkit.xd.model.dog;

public class buydog_edititem_activity extends Activity {

    private ImageView img;
    private RelativeLayout back;
    private EditText tittle;
    private EditText mota;
    private EditText loai;
    private EditText age;
    private EditText cannang;
    private EditText price;
    private EditText address;
    private dog dog1;
    private DatabaseReference mDatabase;
    private Chip chipnam;
    private Chip chipnu;
    private Switch soldswitch;
    private RelativeLayout btneditok;
    private Button btnloadimg;
    Context context;


    int iddog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buydog_edititem);
        getIddog();

        img=findViewById(R.id.editimageView);
        back=findViewById(R.id.frame_17_ek2);
        Init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Init() {
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("dog").child("dog"+String.valueOf(iddog+1)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                tittle= findViewById(R.id.edittitle);
                mota=findViewById(R.id.editmota);
                loai=findViewById(R.id.editloai);
                 age=findViewById(R.id.editage);
                 cannang=findViewById(R.id.editcannang);
                 price=findViewById(R.id.editprice);
                 address=findViewById(R.id.editaddress);
                 chipnam=findViewById(R.id.chip3);
                 chipnu=findViewById(R.id.chip4);
                 soldswitch=findViewById(R.id.switchsold);
                 btneditok= findViewById(R.id.btneditok);
                 btnloadimg =findViewById(R.id.buttonloadimg);

                 String gioitinh;

                if (!task.isSuccessful()) {
                    Toast.makeText(buydog_edititem_activity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                }
                else {
                    dog1 = (dog) task.getResult().getValue(dog.class);
                    tittle.setText(dog1.getTitle());
                    mota.setText(dog1.getMota());
                    age.setText(dog1.getAge());

                    gioitinh = dog1.getGioitinh();

                    price.setText(dog1.getGia()+" VNĐ");
                    loai.setText(dog1.getLoai());
                    if(gioitinh.equals("Male"))
                    {
                        chipnam.setChecked(true);
                    }
                    else
                    {
                        chipnu.setChecked(true);
                    }
                    if (dog1.isTrang_thai())
                       soldswitch.setChecked(false);

                    cannang.setText(String.valueOf(dog1.getCan_nang()));
                    address.setText(dog1.getDiachi());
                    Picasso.get().load(Uri.parse(dog1.getHinhanh())).into(img);

                    tittle.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setTitle(tittle.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    mota.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setMota(mota.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    age.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setAge(age.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    cannang.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setCan_nang(Float.parseFloat(cannang.getText().toString()));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    price.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setGia(price.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    btneditok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("",dog1.toString());
                            mDatabase.child("dog").child("dog"+String.valueOf(iddog+1)).setValue(dog1);
                            finish();
                        }
                    });

                    chipnam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (chipnam.isChecked())
                            {
                                dog1.setGioitinh("Male");
                            }
                        }
                    });

                    chipnu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (chipnu.isChecked())
                            {
                                dog1.setGioitinh("Female");
                            }
                        }
                    });

                    soldswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (soldswitch.isChecked())
                            {
                                dog1.setTrang_thai(true);
                            }
                            else
                            {
                                dog1.setTrang_thai(false);
                            }
                        }
                    });

                    btnloadimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                // action mở file upload

                            // xử lí xong thì save vào dog1
                            dog1.setHinhanh(img.toString());
                        }
                    });
                }
            }
        });

    }

    private void getIddog() {
        iddog = getIntent().getIntExtra("iddog",-1);
        Log.d("id : ",iddog+"");

    }

    private void changetext(TextView textView) {

    }


}