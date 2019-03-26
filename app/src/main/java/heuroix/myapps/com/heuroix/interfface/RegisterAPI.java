package heuroix.myapps.com.heuroix.interfface;

import heuroix.myapps.com.heuroix.json.Value;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterAPI {

    @FormUrlEncoded
    @POST("/insert.php")
    Call<Value> daftar(@Field("nama") String nama,
                       @Field("username") String username,
                       @Field("password") String password,
                       @Field("email") String email,
                       @Field("alamat") String alamat,
                       @Field("notelp") String notelp);
}
