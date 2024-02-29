package ch.protonmail.android.protonmailtest.details

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.databinding.ActivityDetailsBinding
import ch.protonmail.android.protonmailtest.databinding.ItemTaskDatesBinding
import ch.protonmail.android.protonmailtest.util.loadWithPlaceholder
import ch.protonmail.common.AppLogger
import coil.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private val bindingDates by lazy { ItemTaskDatesBinding.bind(binding.root) }

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var appLogger: AppLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        binding.run {
            lifecycleScope.launchWhenResumed {
                viewModel.selectedTask.collect {
                    supportActionBar?.run {
                        title = getString(R.string.task) + " ${it.title}"
                    }
                    title.text = it.title
                    subtitle.text = it.description
                    bindingDates.creationDate.text = it.creationDate
                    bindingDates.dueDate.text = it.dueDate
                    download.isGone = it.shouldDownloadImage
                    download.isEnabled = it.isAppOnline
                    download.setOnClickListener { _ ->
                        download.isEnabled = false
                        image.loadWithPlaceholder(it.image, imageLoader) {
                            listener(
                                onSuccess = { _, _ ->
                                    viewModel.imageDownloadedSuccessfully(it.id)
                                    download.isEnabled = true
                                },
                                onError = { _, _ ->
                                    Toast.makeText(
                                        this@DetailsActivity,
                                        "Download failed!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    download.isEnabled = true
                                }
                            )
                        }
                    }
                    if (it.shouldDownloadImage) image.loadWithPlaceholder(it.image, imageLoader)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
