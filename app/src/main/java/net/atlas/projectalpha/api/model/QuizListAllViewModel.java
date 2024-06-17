package net.atlas.projectalpha.api.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.atlas.projectalpha.api.ApiClient;
import net.atlas.projectalpha.api.ApiResponse;
import net.atlas.projectalpha.api.ApiService;
import net.atlas.projectalpha.api.response.QuizResponse;
import net.atlas.projectalpha.api.response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizListAllViewModel extends ViewModel {
    private MutableLiveData<ApiResponse<QuizResponse[]>> res = new MutableLiveData<>();

    public LiveData<ApiResponse<QuizResponse[]>> getResponse() {
        return res;
    }

    public void fetch(Context context) {
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        Call<ApiResponse<QuizResponse[]>> call = apiService.getQuizListAll();
        call.enqueue(new Callback<ApiResponse<QuizResponse[]>>() {
            @Override
            public void onResponse(Call<ApiResponse<QuizResponse[]>> call, Response<ApiResponse<QuizResponse[]>> response) {
                if (!response.isSuccessful()) {
                    res.setValue(null);
                    return;
                }

                res.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse<QuizResponse[]>> call, Throwable t) {
                System.out.println("Failed");
                res.setValue(null);
            }
        });
    }
}
