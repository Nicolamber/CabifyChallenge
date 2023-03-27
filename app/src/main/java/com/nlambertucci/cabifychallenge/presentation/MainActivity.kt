package com.nlambertucci.cabifychallenge.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nlambertucci.cabifychallenge.R
import com.nlambertucci.cabifychallenge.presentation.products.ProductsListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.cabify_fragment_container, ProductsListFragment.newInstance())
            .commit()

    }
}