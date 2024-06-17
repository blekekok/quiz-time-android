package net.atlas.projectalpha.api;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit != null)
            return retrofit;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();

                        SessionManager sessionManager = new SessionManager(context);

                        String authCookie = sessionManager.getAuthCookie();
                        String refreshCookie = sessionManager.getRefreshCookie();

                        StringBuilder authHeaders = new StringBuilder();

                        if (authCookie != null) {
                            authHeaders.append(authCookie).append("; ");
                        }

                        if (refreshCookie != null) {
                            authHeaders.append(refreshCookie).append("; ");
                        }

                        builder.addHeader("Cookie", authHeaders.toString().trim());

                        System.out.println(authHeaders.toString().trim());

                        Response response = chain.proceed(builder.build());

                        System.out.println(response);

                        if (refreshCookie != null && response.code() == 401) {

                            ApiService apiService = getClient(context).create(ApiService.class);
                            Call<ApiResponse> refreshCall = apiService.refresh();
                            retrofit2.Response<ApiResponse> refreshResponse = refreshCall.execute();

                            if (refreshResponse.isSuccessful()) {
                                String newAuthCookie = refreshResponse.headers().get("Set-Cookie");

                                if (newAuthCookie == null) {
                                    sessionManager.logout();
                                    System.out.println("You got logged out");
                                    return response;
                                }

                                response.close();

                                sessionManager.setAuthCookie(newAuthCookie);

                                builder = chain.request().newBuilder();
                                builder.addHeader("Cookie", newAuthCookie);

                                return chain.proceed(builder.build());
                            }

                            sessionManager.logout();
                            return response;
                        }

                        return response;
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://qt.authenity.net/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
