package net.atlas.projectalpha.api;

import net.atlas.projectalpha.api.request.LoginRequest;
import net.atlas.projectalpha.api.response.QuizDetailResponse;
import net.atlas.projectalpha.api.response.QuizResponse;
import net.atlas.projectalpha.api.response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/login")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/refresh")
    Call<ApiResponse> refresh();

    @GET("user/info")
    Call<ApiResponse<UserInfoResponse>> getUserInfo();

    @GET("quiz/quiz-list-all")
    Call<ApiResponse<QuizResponse[]>> getQuizListAll();

    @GET("quiz/quiz-detail/{quizId}")
    Call<ApiResponse<QuizDetailResponse>> getQuizDetail(@Path("quizId") String quizId);
}

