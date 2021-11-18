package com.test.flicker.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.flicker.R
import com.test.flicker.databinding.ActivityMainBinding
import com.test.flicker.model.Photo
import com.test.flicker.util.AppUtils.hideKeyboard
import com.test.flicker.util.Resource
import com.test.flicker.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SEARCH_DELAY_MS = 200L

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    private val photosAdapter = PhotosAdapter(this::photoSetOnClickListener)
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initUI()
    }

    private fun initUI() {
        binding.rvPhoto.apply {
            this.layoutManager = GridLayoutManager(context, 3)
            this.adapter = photosAdapter
        }

        viewModel.searchPhotos("Cats")

        viewModel.searchPhotosLiveData.observe(this, {
            when(it.status){
                Resource.Status.LOADING -> { updateMessage(R.string.loading_data) }
                Resource.Status.SUCCESS -> {
                    binding.rvPhoto.visibility = VISIBLE
                    with(photosAdapter) {
                        photos.clear()
                        if(it.data?.photos?.photo == null){
                            updateMessage(R.string.no_photo_found)
                        } else {
                            binding.rvPhoto.visibility = VISIBLE
                            photos.addAll(it.data.photos.photo)
                            notifyDataSetChanged()
                            hideKeyboard(binding.root)
                        }
                    }
                }
                else -> { updateMessage(R.string.unexpected_server_response) }
            }
        })

        binding.etSearchText.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(SEARCH_DELAY_MS)
                if(!TextUtils.isEmpty(binding.etSearchText.text.toString())) {
                    viewModel.searchPhotos(binding.etSearchText.text.toString())
                    binding.rvPhoto.visibility = VISIBLE
                    binding.tvMessage.visibility = GONE
                }
            }
        }
    }

    private fun updateMessage(resource: Int) {
        binding.rvPhoto.visibility = GONE
        binding.tvMessage.visibility = VISIBLE
        binding.tvMessage.text = getText(resource)
    }

    private fun photoSetOnClickListener(photo: Photo){
        showFragment(PhotoZoomInZoomOutFragment(photo), "PhotoZoomInZoomOutFragment")
    }

    private fun showFragment(newFragment: DialogFragment, dialogTag: String) {
        // Create and show the dialog.
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        newFragment.show(transaction, dialogTag)
    }

}