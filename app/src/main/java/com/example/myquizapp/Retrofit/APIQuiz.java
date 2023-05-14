package com.example.myquizapp.Retrofit;

import com.example.myquizapp.Model.CategoryModel;
import com.example.myquizapp.Model.LoginModel;
import com.example.myquizapp.Model.QuestionDetailModel;
import com.example.myquizapp.Model.QuestionModel;
import com.example.myquizapp.Model.RankModel;
import com.example.myquizapp.Model.ResultModel;
import com.example.myquizapp.Model.ScoreModel;
import com.example.myquizapp.Model.User2Model;
import com.example.myquizapp.Model.ReceiverModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIQuiz {
    @POST("push_user.php")
    @FormUrlEncoded
    Observable<ReceiverModel> push_user(
        @Field("name") String name,
        @Field("email") String email,
        @Field("pass") String pass,
        @Field("address") String address,
        @Field("loai") int loai
    );

    @POST("check_user.php")
    @FormUrlEncoded
    Observable<LoginModel> check_user(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @GET("get_category.php")
    Observable<CategoryModel> get_category();

    @GET("get_user.php")
    Observable<User2Model> get_user();

    @POST("delete_user.php")
    @FormUrlEncoded
    Observable<ReceiverModel> delete_user(
            @Field("u_id") String u_id
    );

    @POST("update_user.php")
    @FormUrlEncoded
    Observable<ReceiverModel> update_user(
            @Field("u_id") String u_id,
            @Field("name") String name,
            @Field("pass") String pass,
            @Field("address") String address
    );

    @GET("get_question.php")
    Observable<QuestionModel> get_question();

    @POST("delete_question.php")
    @FormUrlEncoded
    Observable<ReceiverModel> delete_question(
            @Field("q_id") String q_id
    );

    @POST("update_question.php")
    @FormUrlEncoded
    Observable<ReceiverModel> update_question(
            @Field("q_id") String q_id,
            @Field("name_question") String name_question,
            @Field("sentence_a") String sentence_a,
            @Field("sentence_b") String sentence_b,
            @Field("sentence_c") String sentence_c,
            @Field("sentence_d") String sentence_d,
            @Field("answer") String answer
    );

    @POST("push_question.php")
    @FormUrlEncoded
    Observable<ReceiverModel> push_question(
            @Field("name_question") String name_question,
            @Field("category") String category,
            @Field("sentence_a") String sentence_a,
            @Field("sentence_b") String sentence_b,
            @Field("sentence_c") String sentence_c,
            @Field("sentence_d") String sentence_d,
            @Field("answer") String answer
    );

    @POST("get_question_detail.php")
    @FormUrlEncoded
    Observable<QuestionDetailModel> get_question_detail(
            @Field("category") String category
    );

    @POST("push_result.php")
    @FormUrlEncoded
    Observable<ReceiverModel> push_result(
            @Field("u_id") String u_id,
            @Field("category") String category,
            @Field("score") int score,
            @Field("time") long time,
            @Field("size") long size
    );

    @POST("check_infor_user.php")
    @FormUrlEncoded
    Observable<ScoreModel> check_infor_user(
            @Field("u_id") String u_id,
            @Field("category") String category
    );

    @POST("update_result.php")
    @FormUrlEncoded
    Observable<ReceiverModel> update_result(
            @Field("u_id") String u_id,
            @Field("category") String category,
            @Field("score") int score,
            @Field("time") long time,
            @Field("size") long size
    );

    @POST("get_result.php")
    @FormUrlEncoded
    Observable<ResultModel> get_result(
            @Field("u_id") String u_id,
            @Field("category") String category
    );

    @POST("get_rank.php")
    @FormUrlEncoded
    Observable<RankModel> get_rank(
            @Field("category") String category
    );

}
