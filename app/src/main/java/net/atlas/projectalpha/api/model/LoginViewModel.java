package net.atlas.projectalpha.api.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.atlas.projectalpha.api.ApiClient;
import net.atlas.projectalpha.api.ApiResponse;
import net.atlas.projectalpha.api.ApiService;
import net.atlas.projectalpha.api.SessionManager;
import net.atlas.projectalpha.api.request.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<ApiResponse> loginResponse = new MutableLiveData<>();

    public LiveData<ApiResponse> getLoginResponse() {
        return loginResponse;
    }

    public void login(String username, String password, Context context) {
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        Call<ApiResponse> call = apiService.login(new LoginRequest(username, password));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    loginResponse.setValue(null);
                    return;
                }

                List<String> cookies = response.headers().values("Set-Cookie");
                String authCookie = null;
                String refreshCookie = null;

                for (String cookie : cookies) {
                    if (cookie.contains("Authentication")) {
                        authCookie = cookie;
                    }

                    if (cookie.contains("Refresh")) {
                        refreshCookie = cookie;
                    }
                }

                SessionManager sessionManager = new SessionManager(context);
                sessionManager.setAuthCookie(authCookie);
                sessionManager.setRefreshCookie(refreshCookie);

                loginResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                loginResponse.setValue(null);
            }
        });
    }
}
