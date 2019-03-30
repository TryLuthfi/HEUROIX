package heuroix.myapps.com.heuroix.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Objects;

import heuroix.myapps.com.heuroix.activity.ContentClicked;
import heuroix.myapps.com.heuroix.json.ContentFoto;
import heuroix.myapps.com.heuroix.R;


public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ProductViewHolder> {


    private Activity mCtx;
    private List<ContentFoto> productList;

    public StaggeredRecyclerViewAdapter(Activity mCtx, List<ContentFoto> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        final ContentFoto product = productList.get(position);
        Glide.with(Objects.requireNonNull(mCtx)).load("https://heuroix.000webhostapp.com/image/" + product.getGambar()).apply(requestOptions).into(holder.imageview_widget);
        holder.name_widget.setText(product.getJudul());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), ContentClicked.class);
                intent.putExtra("id_content", product.getId_content());
                intent.putExtra("judul", product.getJudul());
                intent.putExtra("gambar", product.getGambar());
                intent.putExtra("deskripsi", product.getDeskripsi());
                intent.putExtra("nama", product.getNama());
                intent.putExtra("email", product.getEmail());
                intent.putExtra("userimage", product.getUserimage());
                mCtx.startActivity(intent);
                mCtx.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageview_widget;
        CardView da;
        View view;
        TextView name_widget;


        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            da = itemView.findViewById(R.id.da);
            imageview_widget = itemView.findViewById(R.id.imageview_widget);
            name_widget = itemView.findViewById(R.id.name_widget);

        }
    }
}