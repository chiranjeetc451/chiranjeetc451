package com.mindyug.app.common

import android.net.Uri
import com.mindyug.app.R

data class ProfilePictureState(
    val uri: Uri? = Uri.parse("android.resource://com.mindyug.app/${R.drawable.ic_profile_pic}")
)
