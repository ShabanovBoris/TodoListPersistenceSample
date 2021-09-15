package com.bosha.notespersistencesample.presentation.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bosha.notespersistencesample.R
import com.bosha.notespersistencesample.databinding.FragmentDashboardBinding
import com.bosha.notespersistencesample.domain.entities.Note
import com.bosha.notespersistencesample.presentation.di.MainScreen
import com.bosha.notespersistencesample.presentation.ui.ViewModelFactory
import com.bosha.notespersistencesample.presentation.ui.dashboard.pager.TypeNoteAdapter
import com.bosha.notespersistencesample.presentation.utils.NightModeHelper
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }

    //Manually stop listen the sharedflow after onStop
    private var job: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainScreen).mainScreenComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        // set click listener for night mode switch
        NightModeHelper(requireActivity().applicationContext)
            .setUpNightSwitcher(binding.bottomToolBar.bap)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()

        // add new note
        binding.bottomToolBar.fabPlus.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_additionFragment
            )
        }

        // filter bottom sheet
        binding.bottomToolBar.bap.setNavigationOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_filterDialog
            )
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViewPager() {
        val tableLayout = binding.tableLayout
        val viewPager = binding.viewPager

        val names = mapOf(
            0 to Note.Type.DO.name,
            1 to Note.Type.DOING.name,
            2 to Note.Type.DONE.name,
        )

        viewPager.adapter = TypeNoteAdapter(this)

        TabLayoutMediator(tableLayout, viewPager, true) { tab, pos ->
            tab.text = names[pos]
        }.attach()

        /**
         *  changes listener, simply shows the badge according to the type
         *  when we adding/editing the note
         */
        job = viewModel.changesAlertFlow
            .onEach {
                tableLayout.getTabAt(it)?.apply {
                    orCreateBadge.hasNumber()
                    badge?.badgeGravity = BadgeDrawable.BOTTOM_START
                }
                Log.e("TAG", "get updated type: $it")
            }
            .launchIn(lifecycleScope)
        //and remove badges
        tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.removeBadge()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.removeBadge()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}