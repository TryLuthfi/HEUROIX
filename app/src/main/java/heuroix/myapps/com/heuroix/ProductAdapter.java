package heuroix.myapps.com.heuroix;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Content> productList;
    int satu = 1;
    public static final String KEY_ID_CONTENT = "id_content";
    public static final String KEY_ID_USER = "id_user";
    public static final String URL_LIKEPRESSED = "https://heuroix.000webhostapp.com/like.php";

    public ProductAdapter(Context mCtx, List<Content> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.content_preview, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        final Content product = productList.get(position);
        Glide.with(Objects.requireNonNull(mCtx)).load("https://heuroix.000webhostapp.com/image/" + product.getGambar()).apply(requestOptions).into(holder.gambar);
        if (product.getUserimage().equals("empty")) {
            Glide.with(Objects.requireNonNull(mCtx)).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(holder.userImage);
        } else {
            Glide.with(Objects.requireNonNull(mCtx)).load("https://heuroix.000webhostapp.com/userImage/" + product.getUserimage()).into(holder.userImage);
        }
        holder.nama.setText(product.getNama());
        holder.email.setText(product.getEmail());
        holder.judul.setText("-" + product.getJudul());
        final String id_user = getId_user();
        Glide.with(Objects.requireNonNull(mCtx)).load(R.drawable.likebefore).into(holder.suka);
        holder.suka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(Objects.requireNonNull(mCtx)).load(R.drawable.likeafter).into(holder.suka);
                class AddData extends AsyncTask<Void, Void, String> {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        Toast.makeText(mCtx, s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(KEY_ID_CONTENT, product.getId_content());
                        params.put(KEY_ID_USER, id_user);

                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(URL_LIKEPRESSED, params);
                        return res;
                    }
                }

                AddData ae = new AddData();
                ae.execute();
            }
        });

        holder.gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), ContentClicked.class);
                intent.putExtra("id_content", product.getId_content());
                intent.putExtra("id_user", product.getId_user());
                intent.putExtra("judul", product.getJudul());
                intent.putExtra("gambar", product.getGambar());
                intent.putExtra("deskripsi", product.getDeskripsi());
                intent.putExtra("nama", product.getNama());
                intent.putExtra("email", product.getEmail());
                intent.putExtra("userimage", product.getUserimage());
                mCtx.startActivity(intent);

            }
        });

        holder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), Profile_Preview.class);
                intent.putExtra("id_user", product.getId_user());
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

        TextView judul, jumlahsuka, nama, email;
        LinearLayout user;
        CardView da;
        View view;
        ImageView suka, gambar, userImage;


        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            da = itemView.findViewById(R.id.da);
            judul = itemView.findViewById(R.id.judul);
            jumlahsuka = itemView.findViewById(R.id.jumlahsuka);
            nama = itemView.findViewById(R.id.nama);
            email = itemView.findViewById(R.id.email);
            suka = itemView.findViewById(R.id.suka);
            gambar = itemView.findViewById(R.id.gambar);
            userImage = itemView.findViewById(R.id.userImage);
            user = itemView.findViewById(R.id.user);
        }
    }

    private String getId_user(){
        SharedPreferences preferences = mCtx.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }
}
