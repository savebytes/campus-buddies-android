package com.savebytes.campusbuddy.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import java.util.UUID

class CredentialManagerHelper(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)


    suspend fun getGoogleCredential(
        serverId: String, // Your Web Client ID from Firebase Console
        nonce: String = UUID.randomUUID().toString()
    ): String {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(serverId)
            .setNonce(nonce)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            handleGoogleIdSignIn(result)
        } catch (e: Exception) {
            throw Exception("Google Sign-in failed: ${e.message}")
        }
    }

    private fun handleGoogleIdSignIn(result: GetCredentialResponse): String {
        val credential = result.credential
        val googleIdTokenCredential = com.google.android.libraries.identity.googleid
            .GoogleIdTokenCredential.createFrom(credential.data)
        return googleIdTokenCredential.idToken
    }
}