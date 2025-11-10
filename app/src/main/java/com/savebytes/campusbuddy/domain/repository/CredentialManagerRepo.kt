package com.savebytes.campusbuddy.domain.repository

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

interface CredentialManagerRepo {
    suspend fun getGoogleCredential(): GoogleIdTokenCredential?
}