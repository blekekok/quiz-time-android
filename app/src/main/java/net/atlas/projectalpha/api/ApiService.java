package net.atlas.projectalpha.api;

import net.atlas.projectalpha.api.request.LoginRequest;
import net.atlas.projectalpha.api.response.QuizResponse;
import net.atlas.projectalpha.api.response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/login")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/refresh")
    Call<ApiResponse> refresh();

    @GET("user/info")
    Call<ApiResponse<UserInfoResponse>> getUserInfo();

    @GET("quiz/quiz-list-all")
    Call<ApiResponse<QuizResponse[]>> getQuizListAll();
}

