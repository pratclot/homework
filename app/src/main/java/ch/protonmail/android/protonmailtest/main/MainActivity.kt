package ch.protonmail.android.protonmailtest.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.databinding.ActivityMainBinding
import ch.protonmail.android.protonmailtest.details.DetailsActivity
import ch.protonmail.android.protonmailtest.pager.TabsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * TODO a terrible hack to init the ViewModel :)
         */
        viewModel
        setContentView(binding.root)

        initTabs()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.taskClickedEvent.collect {
                    Intent(this@MainActivity, DetailsActivity::class.java)
                        .let(::startActivity)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.tasksAllCount.collect {
                    binding.tabLayout.getTabAt(0)?.text = getString(
                        R.string.all_tasks,
                        it
                    )
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.tasksUpcomingCount.collect {
                    binding.tabLayout.getTabAt(1)?.text = getString(
                        R.string.upcoming_tasks,
                        it
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.run {
            title = getString(R.string.app_name)
            setIcon(R.drawable.ic_no_internet)
            lifecycleScope.launchWhenResumed {
                viewModel.showOfflineIndicator.collect {
                    setDisplayShowHomeEnabled(it)
                }
            }
        }
    }

    private fun initTabs() {
        val adapter = TabsAdapter(
            fetchString = {
                getString(
                    if (it == 0) R.string.all_tasks else R.string.upcoming_tasks,
                    0
                )
            },
            fragmentManager = supportFragmentManager
        )
        binding.pager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.pager)
    }
}
