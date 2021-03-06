package com.timelysoft.tsjdomcom.service

import com.example.kotlinscreenscanner.service.model.*
import com.example.myapplication.model.ResultModel
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

interface ApiService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("login?token=oYyxhIFgJjAb")
    suspend fun auth(@FieldMap params: Map<String, String>): Response<CommonResponse<ResultModel>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("checkPhone?token=oYyxhIFgJjAb")
    suspend fun numberPhone(@FieldMap params: Map<String, String>): Response<CommonResponse<ResultPhoneModel>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("checkCode?token=oYyxhIFgJjAb")
    suspend fun smsConfirmation(@FieldMap params: Map<String, Int>): Response<CommonResponse<SmsResultModel>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("registration?token=oYyxhIFgJjAb")
    suspend fun questionnaire(@FieldMap map: Map<String, String>): Response<CommonResponse<QuestionnaireResultModel>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("listGender?token=oYyxhIFgJjAb")
    suspend fun listGender(@FieldMap params: Map<String, Int>): Response<CommonResponse<ArrayList<ListGenderResultModel>>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("listNationality?token=oYyxhIFgJjAb")
    suspend fun listNationality(@FieldMap params: Map<String, Int>): Response<CommonResponse<ArrayList<ListNationalityResultModel>>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("listSecretQuestion?token=oYyxhIFgJjAb")
    suspend fun listSecretQuestion(@FieldMap params: Map<String, Int>): Response<CommonResponse<ArrayList<ListSecretQuestionResultModel>>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("listAvailableCountry?token=oYyxhIFgJjAb")
    suspend fun listAvailableCountry(@FieldMap params: Map<String, Int>): Response<CommonResponse<ArrayList<CounterResultModel>>>
}

