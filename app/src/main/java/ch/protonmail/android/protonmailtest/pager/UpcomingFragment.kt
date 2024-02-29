package ch.protonmail.android.protonmailtest.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ch.protonmail.android.protonmailtest.databinding.FragmentUpcomingBinding
import ch.protonmail.android.protonmailtest.main.MainViewModel
import coil.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    private val binding by lazy { FragmentUpcomingBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val adapter = TasksAdapter(
            imageLoader = imageLoader,
            taskClicked = { viewModel.taskClicked(it) }
        )
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        lifecycleScope.launchWhenResumed {
            viewModel.tasksUpcoming.collect {
                adapter.submitList(it)
            }
        }
    }
}
