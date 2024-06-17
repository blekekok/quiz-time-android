package net.atlas.projectalpha.api.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.atlas.projectalpha.api.ApiClient;
import net.atlas.projectalpha.api.ApiResponse;
import net.atlas.projectalpha.api.ApiService;
import net.atlas.projectalpha.api.response.QuizDetailResponse;
import net.atlas.projectalpha.api.response.QuizResponse;
import net.atlas.projectalpha.api.response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizDetailViewModel extends ViewModel {
    private MutableLiveData<ApiResponse<QuizDetailResponse>> res = new MutableLiveData<>();

    public LiveData<ApiResponse<QuizDetailResponse>> getResponse() {
        return res;
    }

    public void fetch(String id, Context context) {
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        Call<ApiResponse<QuizDetailResponse>> call = apiService.getQuizDetail(id);
        call.enqueue(new Callback<ApiResponse<QuizDetailResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<QuizDetailResponse>> call, Response<ApiResponse<QuizDetailResponse>> response) {
                if (!response.isSuccessful()) {
                    res.setValue(null);
                    return;
                }

                res.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse<QuizDetailResponse>> call, Throwable t) {
                res.setValue(null);
            }
        });
    }
}
