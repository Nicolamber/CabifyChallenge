package com.nlambertucci.cabifychallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
    Clase utilitaria para que dagger tenga acceso al context
 */
@HiltAndroidApp
class CabifyApplication : Application()