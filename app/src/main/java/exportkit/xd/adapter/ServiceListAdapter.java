package exportkit.xd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.model.dichvu;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder>{
    private ArrayList<dichvu> dichvus;
    private int id;

    public ServiceListAdapter(ArrayList<dichvu> dichvus) {
        this.dichvus = dichvus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_service, parent, false);

        return new ViewHolder(view);
    }
    public OnBindCallback onBind;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (onBind != null) {
            onBind.onViewBound(holder, position);
        }
        holder.title.setText(dichvus.get(position).getTitle());
        holder.gia.setText(dichvus.get(position).getGia()+" VNĐ");
        Picasso.get().load(dichvus.get(position).getHinhanh()).into(holder.img);
        id = holder.getLayoutPosition();
    }


    @Override
    public int getItemCount() {
        return dichvus.size();
    }

    public int getId(int position) {
        return dichvus.get(position).getId();
    }

    public void delete(int ind){
        dichvus.remove(ind);
        notifyDataSetChanged();
    }

    public void update(dichvu dichvu,int id){
        dichvus.set(id,dichvu);
        notifyItemChanged(id);
    }

    public void updateList(ArrayList<dichvu> list){
        dichvus = list;
        notifyDataSetChanged();
    }

    public int getIndex(){
        return id;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView img;
        private final TextView gia;

        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView)view.findViewById(R.id.rectangle_24);
            gia = (TextView)view.findViewById(R.id.gia);
        }
    }

    public interface OnBindCallback {
        void onViewBound(ServiceListAdapter.ViewHolder viewHolder, int position);
    }

}
