package heuroix.myapps.com.heuroix;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<ContentFoto> productList;

    public StaggeredRecyclerViewAdapter(Context mCtx, List<ContentFoto> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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