package com.example.notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.notes.R
import com.example.notes.database.NoteDatabase
import com.example.notes.database.NotesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val notesRepository = NotesRepository(NoteDatabase.getDatabase(applicationContext))
        val notesRepository = NotesRepository(NoteDatabase(this))
        val viewModelProviderFactory = NotesViewModelProviderFactory(notesRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NotesViewModel::class.java]


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHost = supportFragmentManager.findFragmentById(R.id.notesNavHostFragment) as NavHostFragment
        navController = navHost.navController

        NavigationUI.setupWithNavController(bottomNavigation, navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}