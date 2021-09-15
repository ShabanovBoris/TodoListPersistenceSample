package com.bosha.notespersistencesample.presentation.ui.dashboard.pager

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosha.notespersistencesample.R
import com.bosha.notespersistencesample.databinding.FragmentPagerBinding
import com.bosha.notespersistencesample.domain.common.NotesResult
import com.bosha.notespersistencesample.domain.entities.Note
import com.bosha.notespersistencesample.presentation.di.MainScreen
import com.bosha.notespersistencesample.presentation.ui.MainViewModel
import com.bosha.notespersistencesample.presentation.ui.ViewModelFactory
import com.bosha.notespersistencesample.presentation.ui.addition.AdditionFragment
import com.bosha.notespersistencesample.presentation.utils.launchInWhenStarted
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class PagerFragment : Fragment(R.layout.fragment_pager) {

    private var _binding: FragmentPagerBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var job: Job? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    private var recyclerAdapter: NotesRecyclerAdapter? = null

    private val argNoteType by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getSerializable(TYPE) ?: Note.Type.DO
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainScreen).mainScreenComponent
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPagerBinding.bind(view)

        initRecycler()

        job = viewModel.notesList
            .onEach(::handleResult)
            .launchInWhenStarted(lifecycleScope)
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerAdapter = null
        binding.rvNotesList.adapter = null
    }

    private fun initRecycler() {
        recyclerAdapter = NotesRecyclerAdapter().apply {
            setOnEditListener {
                findNavController().navigate(
                    R.id.action_dashboardFragment_to_additionFragment,
                    Bundle(2).apply {
                        putBoolean(AdditionFragment.KEY_IS_EDIT, true)
                        putParcelable(AdditionFragment.KEY_NOTE_ARG, it)
                    }
                )
            }
        }
        binding.rvNotesList.apply {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }
    }

    private fun handleResult(result: NotesResult) {
        val adapter = requireNotNull(recyclerAdapter)
        when (result) {
            NotesResult.EmptyResult -> {
                adapter.submitList(emptyList())
            }
            is NotesResult.ValidResult -> {

                val oldListSize = adapter.itemCount

                when (argNoteType) {
                    Note.Type.DO -> adapter.submitList(result.doList) {
                        //scroll up when list has new item
                        result.doList
                            ?.let {
                                if (it.size > oldListSize) binding.rvNotesList.scrollToPosition(0)
                            }
                    }
                    Note.Type.DOING -> adapter.submitList(result.doingList) {
                        result.doingList
                            ?.let {
                                if (it.size > oldListSize) binding.rvNotesList.scrollToPosition(0)
                            }
                    }
                    Note.Type.DONE -> adapter.submitList(result.doneList) {
                        result.doneList
                            ?.let {
                                if (it.size > oldListSize) binding.rvNotesList.scrollToPosition(0)
                            }
                    }
                }
            }
            NotesResult.EmptySearch -> {
            }
        }
    }

    companion object {
        const val TYPE = "type"
    }
}
