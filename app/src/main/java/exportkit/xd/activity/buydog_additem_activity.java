package exportkit.xd.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import exportkit.xd.R;

public class buydog_additem_activity extends Activity {
    private ImageView img;
    private RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buydog_additem);

        img=findViewById(R.id.imageView);
        back=findViewById(R.id.frame_17_ek2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/fir-demo-90f36.appspot.com/o/wiggle_logo.png?alt=media&token=75f58293-713f-400a-a374-7a532f1dbc7b")).into(img);
    }
}