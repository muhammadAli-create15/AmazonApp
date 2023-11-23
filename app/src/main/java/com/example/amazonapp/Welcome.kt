package com.example.amazonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class Welcome : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Set a custom indicator icon for the ActionBarDrawerToggle
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

// Handle the ActionBarDrawerToggle click event to open the navigation drawer
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        toggle.setToolbarNavigationClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
         when (menuItem.itemId) {
            R.id.nav_cart -> {
                openFragment(CartFragment())

            }
            R.id.nav_orders -> {
                openFragment(OrdersFragment())

            }
            R.id.nav_categories -> {
                openFragment(CategoriesFragment())

            }
            R.id.nav_settings -> {

            }
            R.id.nav_logout -> {
                openFragment(LogoutFragment())

            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

     }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction:FragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_content,fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {

            //drawerLayout.openDrawer(GravityCompat.START)
            super.onBackPressedDispatcher.onBackPressed()
        }
    }


}