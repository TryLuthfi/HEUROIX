package heuroix.myapps.com.heuroix.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import heuroix.myapps.com.heuroix.activity.SplashScreen;
import heuroix.myapps.com.heuroix.json.Content;
import heuroix.myapps.com.heuroix.activity.ContentClicked;
import heuroix.myapps.com.heuroix.activity.Profile_Preview;
import heuroix.myapps.com.heuroix.R;
import heuroix.myapps.com.heuroix.konfigurasi.konfigurasi;
import heuroix.myapps.com.heuroix.request.RequestHandler;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<Content> productList;
    public Activity mContext;
    String id_content;
    DownloadManager downloadManager;
    public static final String KEY_ID_CONTENT = "id_content";
    public static final String KEY_ID_USER = "id_user";
    public static final String URL_LIKEPRESSED = "https://heuroix.000webhostapp.com/Like.php";


    public ProductAdapter(Activity mCtx, List<Content> productList) {
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

        Toast.makeText(mCtx, "" + SplashScreen.id_content, Toast.LENGTH_SHORT).show();

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
        final String id_userr = getId_user();
        if (SplashScreen.id_userr.equals(id_userr) && SplashScreen.id_content.equals(product.getId_content())) {
            Glide.with(Objects.requireNonNull(mCtx)).load(R.drawable.likeafter).into(holder.suka);
        } else {
            Glide.with(Objects.requireNonNull(mCtx)).load(R.drawable.likebefore).into(holder.suka);
        }
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
                        params.put(KEY_ID_USER, id_userr);

                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(URL_LIKEPRESSED, params);
                        return res;
                    }
                }

                AddData ae = new AddData();
                ae.execute();
            }
        });
        holder.popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mCtx);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                String id_user = getId_user();
                if (id_user.equals(product.getId_user())) {
                    dialog.setContentView(R.layout.custom_button_for_user);
                    TextView delete = dialog.findViewById(R.id.delete);
                    TextView download = dialog.findViewById(R.id.download);
                    TextView edit = dialog.findViewById(R.id.edit);

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog2 = new Dialog(mCtx);
                            dialog2.setCancelable(true);
                            dialog2.setCanceledOnTouchOutside(true);
                            dialog2.setContentView(R.layout.custom_popup_dialog);
                            dialog.dismiss();

                            Button tidak = dialog2.findViewById(R.id.tidak);
                            Button ya = dialog2.findViewById(R.id.ya);

                            ya.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    class AddData extends AsyncTask<Void, Void, String> {

                                        ProgressDialog loading;

                                        @Override
                                        protected void onPreExecute() {
                                            super.onPreExecute();
                                            loading = ProgressDialog.show(mCtx, "Sedang Dihapus...", "Tunggu...", false, false);
                                        }

                                        @Override
                                        protected void onPostExecute(String s) {
                                            super.onPostExecute(s);
                                            loading.dismiss();
                                            dialog.dismiss();
                                            dialog2.dismiss();
                                            Toast.makeText(mCtx, s, Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        protected String doInBackground(Void... v) {
                                            HashMap<String, String> params = new HashMap<>();
                                            params.put(KEY_ID_CONTENT, product.getId_content());

                                            RequestHandler rh = new RequestHandler();
                                            String res = rh.sendPostRequest(konfigurasi.URL_DELETE_CONTENT, params);
                                            return res;
                                        }
                                    }

                                    AddData ae = new AddData();
                                    ae.execute();
                                }
                            });
                            tidak.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    dialog2.dismiss();
                                }
                            });

                            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog2.show();
                            Window window = dialog2.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        }
                    });

                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            downloadManager = (DownloadManager) mCtx.getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse("https://heuroix.000webhostapp.com/image/" + product.getGambar());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            Long reference = downloadManager.enqueue(request);
                            dialog.dismiss();
                        }
                    });

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mCtx, "belum ada", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    dialog.setContentView(R.layout.custom_button);
                    TextView download = dialog.findViewById(R.id.download);

                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            downloadManager = (DownloadManager) mCtx.getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse("https://heuroix.000webhostapp.com/image/" + product.getGambar());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            Long reference = downloadManager.enqueue(request);
                            dialog.dismiss();
                        }
                    });
                }

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                mCtx.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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
                mCtx.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
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
        ImageView suka, gambar, userImage, popup;


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
            popup = itemView.findViewById(R.id.popup);
        }
    }

    private String getId_user() {
        SharedPreferences preferences = mCtx.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }

}
