package exportkit.xd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.model.dog;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.ViewHolder>{
    private ArrayList<dog> dogArrayList;
    private int id;

    public DogListAdapter(ArrayList<dog> dogs) {
        this.dogArrayList = dogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_dog, parent, false);
        return new ViewHolder(view);
    }
    public OnBindCallback onBind;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (onBind != null) {
            onBind.onViewBound(holder, position);
        }
        holder.title.setText(dogArrayList.get(position).getTitle());
        holder.gia.setText(dogArrayList.get(position).getGia()+" VNĐ");
        Picasso.get().load(dogArrayList.get(position).getHinhanh()).into(holder.img);
        id = holder.getLayoutPosition();

    }


    @Override
    public int getItemCount() {
        return dogArrayList.size();
    }

    public String getIdchu(int position) {
        return dogArrayList.get(position).getIdchu();
    }

    public int getId(int position) {
        return dogArrayList.get(position).getId();
    }

    public void delete(int ind){
        dogArrayList.remove(ind);
        notifyDataSetChanged();
    }

    public void update(dog dog,int id){
        dogArrayList.set(id,dog);
        notifyItemChanged(id);
    }

    public void updateList(ArrayList<dog> list){
        dogArrayList = list;
        notifyDataSetChanged();
    }

    public int getIndex(){
        return id;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView img;
        private final TextView gia;
        private final RelativeLayout frame;

        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView)view.findViewById(R.id._rectangle_5_ek1);
            gia = (TextView)view.findViewById(R.id.gia);
            frame = (RelativeLayout)view.findViewById(R.id.frame_9_ek1);
        }
    }

    public interface OnBindCallback {
        void onViewBound(ViewHolder viewHolder, int position);
    }

}
