package com.bosha.notespersistencesample.presentation.ui.dashboard.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bosha.notespersistencesample.domain.entities.Note

class TypeNoteAdapter(fragment: Fragment)
    : FragmentStateAdapter(fragment.childFragmentManager, fragment.viewLifecycleOwner.lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = PagerFragment()
        when (position){
            0 -> {
                fragment.arguments =
                    Bundle(1).apply {
                        putSerializable(PagerFragment.TYPE, Note.Type.DO)
                    }
            }
            1 -> {
                fragment.arguments =
                    Bundle(1).apply {
                        putSerializable(PagerFragment.TYPE, Note.Type.DOING)
                    }
            }
            2 -> {
                fragment.arguments =
                    Bundle(1).apply {
                        putSerializable(PagerFragment.TYPE, Note.Type.DONE)
                    }
            }

        }
        return fragment
    }
}