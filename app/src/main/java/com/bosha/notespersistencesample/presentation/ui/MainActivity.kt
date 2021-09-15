package com.bosha.notespersistencesample.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.bosha.notespersistencesample.NotesApp
import com.bosha.notespersistencesample.R
import com.bosha.notespersistencesample.data.utils.DataStorePreference
import com.bosha.notespersistencesample.databinding.ActivityMainBinding
import com.bosha.notespersistencesample.presentation.di.MainScreen
import com.bosha.notespersistencesample.presentation.di.MainScreenComponent
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainScreen {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_container)
    }
    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph, binding.drawerLayout)
    }

    override val mainScreenComponent: MainScreenComponent by lazy {
        (application as NotesApp).appComponent
            .plusMainScreenComponent().create(this).also {
                it.inject(this)
            }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolBar)
    }

    override fun onStart() {
        super.onStart()
        /**
         * Set up automatically change back/hamburger button in toolbar for drawer
         */
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )
        setUpDrawerButtons()
        initDataStoreChangeListener()
    }

    private fun initDataStoreChangeListener() {
        DataStorePreference(this.applicationContext,this){
            viewModel.onDataStoreChanged()
        }
    }

    private fun setUpDrawerButtons() {
        binding.navViewDrawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.clearData -> {
                    viewModel.clearData()
                    binding.drawerLayout.close()
                }
                R.id.settingsFragment -> {
                    if (navController.currentDestination?.id != R.id.settingsFragment )
                        navController.navigate(R.id.action_dashboardFragment_to_settingsFragment)
                    binding.drawerLayout.close()
                }
                R.id.dashboardFragment -> {
                    if (navController.currentDestination?.id != R.id.dashboardFragment )
                        navController.navigate(R.id.dashboardFragment)
                    binding.drawerLayout.close()
                }
            }
            true
        }
    }

    /**
     * Open the drawer menu
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}