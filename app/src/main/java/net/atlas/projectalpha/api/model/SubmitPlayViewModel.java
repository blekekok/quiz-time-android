package net.atlas.projectalpha.api.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.atlas.projectalpha.api.ApiClient;
import net.atlas.projectalpha.api.ApiResponse;
import net.atlas.projectalpha.api.ApiService;
import net.atlas.projectalpha.api.request.SubmitPlayRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitPlayViewModel extends ViewModel {
    private MutableLiveData<ApiResponse> res = new MutableLiveData<>();

    public LiveData<ApiResponse> getResponse() {
        return res;
    }

    public void send(String quizId, ArrayList<Integer> answer, Context context) {
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        Call<ApiResponse> call = apiService.submitPlay(new SubmitPlayRequest(quizId, answer));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                System.out.println("Submit");
                System.out.println(response);
                if (!response.isSuccessful()) {
                    res.setValue(null);
                    return;
                }

                res.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                res.setValue(null);
            }
        });
    }
}
