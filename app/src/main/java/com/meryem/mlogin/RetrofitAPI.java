

package com.meryem.mlogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("register")

    Call<DataModal> createPost(@Body DataModal dataModal);

    @POST("login")

    Call<DataModal> createLogin(@Body DataModal dataModal);


}
