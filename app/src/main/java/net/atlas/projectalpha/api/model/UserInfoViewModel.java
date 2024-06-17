package net.atlas.projectalpha.api.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.atlas.projectalpha.api.ApiClient;
import net.atlas.projectalpha.api.ApiResponse;
import net.atlas.projectalpha.api.ApiService;
import net.atlas.projectalpha.api.response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoViewModel extends ViewModel {
    private MutableLiveData<ApiResponse<UserInfoResponse>> res = new MutableLiveData<>();

    public LiveData<ApiResponse<UserInfoResponse>> getResponse() {
        return res;
    }

    public void fetchUserInfo(Context context) {
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        Call<ApiResponse<UserInfoResponse>> call = apiService.getUserInfo();
        call.enqueue(new Callback<ApiResponse<UserInfoResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserInfoResponse>> call, Response<ApiResponse<UserInfoResponse>> response) {
                if (!response.isSuccessful()) {
                    res.setValue(null);
                    return;
                }

                res.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse<UserInfoResponse>> call, Throwable t) {
                System.out.println("Failed");
                res.setValue(null);
            }
        });
    }
}
