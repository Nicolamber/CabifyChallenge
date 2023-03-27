package com.nlambertucci.cabifychallenge.common

import android.view.View

/*
 * Extension for all View-type components that allows handling their visibility using booleans.
 */
var View.isVisible: Boolean
    get() {
        return this.visibility == View.VISIBLE
    }
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }