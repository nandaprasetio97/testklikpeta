package com.example.todo.presentation.main.fragment.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.todo.CreateTodoSealed
import com.example.todo.R
import com.example.todo.databinding.FragmentCreateTodoBinding
import com.example.todo.presentation.main.MainViewModel
import com.example.todo.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@AndroidEntryPoint
class TodoCreateFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mBinding: FragmentCreateTodoBinding
    private lateinit var mMaps: MapView
    private var mTodoId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLiveDataState()
        registerLiveData()
        initMap()
        getTodoById()
    }

    private fun registerLiveData() {
        mainViewModel.observerLatLng.observe(viewLifecycleOwner, {
            val geoPoint = GeoPoint(it.latitude, it.longitude)
            generateOverlay(geoPoint)
        })
        mainViewModel.observerDate.observe(viewLifecycleOwner, {
            mBinding
                .invalidateAll()
        })
    }

    private fun initMap() {
        mMaps = mBinding.osmdroid
        mMaps.controller.setZoom(14.0)
        mMaps.setBuiltInZoomControls(false)
        mMaps.setMultiTouchControls(true)
    }

    private fun generateOverlay(geoPoint: GeoPoint) {

    }

    private val eventsReceiver: MapEventsReceiver by lazy {
        object: MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                TODO("Not yet implemented")
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                TODO("Not yet implemented")
            }
        }
    }

    private fun fragmentLiveDataState() {
        mainViewModel.observerSave.observe(viewLifecycleOwner, {
            when (it) {
                is CreateTodoSealed.OnProgressGet -> mBinding.getOnProgress = true
                is CreateTodoSealed.OnProgressSave -> mBinding.saveOnProgress = true
                is CreateTodoSealed.OnSaveSuccess -> {
                    mBinding.saveOnProgress = false
                    mainViewModel.getTodoList()
                    Navigation.findNavController(mBinding.root)
                        .navigate(R.id.action_todoCreateFragment_to_todoListFragment)
                }
                is CreateTodoSealed.OnGetSuccess -> {
                    mBinding.getOnProgress = false
                    mBinding.todo = it.todo
                }
                is CreateTodoSealed.OnFailure -> onError(it.err)
            }
        })
    }

    private fun onError(e: Throwable) {
        mBinding.getOnProgress = false
        mBinding.saveOnProgress = false
        DialogUtils.alertDialogError(requireContext(), e)
    }

    private fun getTodoById() {
        mainViewModel.getTodoById(mTodoId!!)
    }
}