package com.dicoding.harvestscan.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("userCredential")
	val userCredential: UserCredential

)

data class ProviderDataItem(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("photoURL")
	val photoURL: Any,

	@field:SerializedName("phoneNumber")
	val phoneNumber: Any,

	@field:SerializedName("providerId")
	val providerId: String,

	@field:SerializedName("displayName")
	val displayName: Any,

	@field:SerializedName("email")
	val email: String
)

data class User(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("emailVerified")
	val emailVerified: Boolean,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("isAnonymous")
	val isAnonymous: Boolean,

	@field:SerializedName("stsTokenManager")
	val stsTokenManager: StsTokenManager,

	@field:SerializedName("lastLoginAt")
	val lastLoginAt: String,

	@field:SerializedName("apiKey")
	val apiKey: String,

	@field:SerializedName("providerData")
	val providerData: List<ProviderDataItem>,

	@field:SerializedName("appName")
	val appName: String,

	@field:SerializedName("email")
	val email: String
)

data class UserCredential(

	@field:SerializedName("providerId")
	val providerId: Any,

	@field:SerializedName("_tokenResponse")
	val tokenResponse: TokenResponse,

	@field:SerializedName("operationType")
	val operationType: String,

	@field:SerializedName("user")
	val user: User
)

data class TokenResponse(

	@field:SerializedName("expiresIn")
	val expiresIn: String,

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("idToken")
	val idToken: String,

	@field:SerializedName("registered")
	val registered: Boolean,

	@field:SerializedName("localId")
	val localId: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)

data class StsTokenManager(

	@field:SerializedName("expirationTime")
	val expirationTime: Long,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
