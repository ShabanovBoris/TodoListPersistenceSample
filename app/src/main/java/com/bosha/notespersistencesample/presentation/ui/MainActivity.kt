package com.bosha.notespersistencesample.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.bosha.notespersistencesample.NotesApp
import com.bosha.notespersistencesample.R
import com.bosha.notespersistencesample.databinding.ActivityMainBinding
import com.bosha.notespersistencesample.presentation.di.MainScreen
import com.bosha.notespersistencesample.presentation.di.MainScreenComponent
import com.google.android.material.navigation.NavigationView
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainScreen {

    override lateinit var mainScreenComponent: MainScreenComponent

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {

        mainScreenComponent = (application as NotesApp).appComponent
            .plusMainScreenComponent().create().also {
                it.inject(this)
            }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolBar)
    }

    override fun onStart() {
        super.onStart()
        val drawerLayout: DrawerLayout = binding.drawerLayout
        navController = findNavController(R.id.nav_host_fragment_container)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.aboutFragment,
            R.id.dashboardFragment
        ), drawerLayout)
        NavigationUI.setupWithNavController(binding.navViewDrawer,navController)
        /**
         * Set up automatically change back/hamburger button in toolbar for drawer
         */
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )



        //TODO refactor with proper id's
//        binding.navViewDrawer.setNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.clearData -> {
//                    viewModel.clearData()
//                    drawerLayout.close()
//                }
////                R.id.navigation_home -> {
////                    if (navController.currentDestination?.id == R.id.aboutFragment )
////                        navController.navigate(R.id.action_aboutFragment_to_dashboardFragment)
////                    drawerLayout.close()
////                }
////                R.id.navigation_about -> {
////                    if (navController.currentDestination?.id == R.id.dashboardFragment )
////                    navController.navigate(R.id.action_dashboardFragment_to_aboutFragment)
////                    drawerLayout.close()
////                }
//            }
//            true
//        }
    }


    /**
     * Open the drawer menu
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}