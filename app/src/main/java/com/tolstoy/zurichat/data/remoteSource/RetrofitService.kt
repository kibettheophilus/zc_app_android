package com.tolstoy.zurichat.data.remoteSource

import com.tolstoy.zurichat.models.*
import com.tolstoy.zurichat.models.OrganizationModel.OrganizationCreator
import com.tolstoy.zurichat.models.OrganizationModel.OrganizationCreatorResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


 //'https://api.zuri.chat/v1/auth/login'

interface RetrofitService {

    @POST("auth/login")
    suspend fun login(@Body loginBody: LoginBody): LoginResponse

    @POST("account/request-password-reset-code")
    suspend fun passwordreset(@Body passwordReset: PasswordReset): PassswordRestReponse

    @POST("organizations")
    suspend fun createOrganization(@Body organizationCreator: OrganizationCreator): OrganizationCreatorResponse

    @POST("users")
    fun register(@Body registerUser: RegisterUser?): Call<RegisterUser?>?

    @POST ("account/verify-account")
    fun verifyEmail(@Body verifyEmail : VerifyEmail?): Call<VerifyEmail?>?

    @GET("users")
    suspend fun getUsers(@Header("Authorization")token: String): Response<UserList>

}


