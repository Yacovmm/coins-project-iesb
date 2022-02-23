package com.example.iesbcoinapp.presentation.main

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import androidx.core.app.NotificationCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.example.iesbcoinapp.R
import com.example.iesbcoinapp.core.utils.toggleVisibility
import com.example.iesbcoinapp.databinding.MainFragmentBinding
import com.example.iesbcoinapp.infra.AlarmManagerHelper
import com.example.iesbcoinapp.infra.HourAndMinute
import com.example.iesbcoinapp.presentation.MainActivity
import com.example.iesbcoinapp.presentation.alarm.AlarmFragmentDialog
import com.example.iesbcoinapp.presentation.base.BaseFragment
import com.example.iesbcoinapp.presentation.base.GenericListSkeleton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment :
    BaseFragment<MainFragmentBinding>() {

    private val viewModel: MainViewModel by viewModels()

    // Adapter
    @Inject
    lateinit var mainAdapter: MainAdapter

    @Inject
    lateinit var favoriteAdapter: MainAdapter

    private lateinit var concatAdapter: ConcatAdapter

    private lateinit var skeleton: GenericListSkeleton

    private lateinit var picker: MaterialTimePicker


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding
        get() = MainFragmentBinding::inflate

    override fun setupFragment() {
        setupRecyclerView()

        setupSkeleton()

        setupObservers()

        initTimePicker()

        teste()
    }

    private fun initTimePicker() {
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext().applicationContext)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Selecione o alarme")
            .build()

        picker.addOnPositiveButtonClickListener {
            println("${picker.hour} ${picker.minute}" )
            AlarmManagerHelper.setAlarm(
                requireContext(),
                HourAndMinute(picker.hour, picker.minute)
            )
        }
        picker.addOnNegativeButtonClickListener {
            // call back code
        }
        picker.addOnCancelListener {
            // call back code
        }
        picker.addOnDismissListener {
            // call back code
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun teste() {
        binding.lastUpdated.setOnClickListener {
            createNotification()
        }
    }

    private fun createNotification() {
        val notificationIntent = Intent(context, MainActivity::class.java)

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        notificationIntent.putExtra("title", "Teste IESB")
        notificationIntent.putExtra("body", "Teste Alunos")

        val pendingIntent =
            PendingIntent.getActivity(
                context,0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val channelId = "1"
        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setContentTitle("Teste Iesb")
            .setTicker("Coin Worker")
            .setContentText("Teste Msg")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(false) // Nao pode ser cancelada pelo user
            .setAutoCancel(true)
            .addAction(android.R.drawable.ic_delete, "Cancel", pendingIntent)
            .setContentIntent(pendingIntent)

        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                "Channel Iesb",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            builder.setChannelId(notificationChannel.id)
        }
        notificationManager.notify(0, builder.build())
    }

    private fun setupSkeleton() {
        skeleton = GenericListSkeleton(binding.recyclerview, R.layout.recyclerview_cell_icon)
        skeleton.showSkeletons()
    }

    private fun setupObservers() {
        viewModel.coinsData.observe(viewLifecycleOwner) { data ->
            if (data.isEmpty()) {
                binding.placeholder.placeholderContainer.toggleVisibility(true)

                skeleton.hideSkeletons()
                return@observe
            }
            binding.placeholder.placeholderContainer.toggleVisibility(false)

            skeleton.hideSkeletons()
            binding.lastUpdated.text = getString(R.string.last_updated_str, data.first().update_at)

            val favoritesCoins = data.filter { it.isFavourite }

            favoriteAdapter.submitList(favoritesCoins.toList())
            mainAdapter.submitList(data.toList())
        }
    }

    private fun setupRecyclerView() {
        mainAdapter.setOnStarClick { id, status ->
            viewModel.favouriteCoin(id, status)
        }
        favoriteAdapter.setOnStarClick { id, status ->
            viewModel.favouriteCoin(id, status)
        }
        concatAdapter = ConcatAdapter()
        concatAdapter.addAdapter(favoriteAdapter)
        concatAdapter.addAdapter(mainAdapter)
        binding.recyclerview.adapter = concatAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_alarm -> {
                println("Teste")
//                AlarmFragmentDialog().show(childFragmentManager, "ALARM_TAG")
                picker.show(childFragmentManager, "TAG")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.topbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

//    private val diffUtil = object : DiffUtil.ItemCallback<CoinEntity>() {
//        override fun areItemsTheSame(
//            oldItem: CoinEntity,
//            newItem: CoinEntity
//        ): Boolean {
//            return oldItem.hashCode() == newItem.hashCode()
//        }
//
//        override fun areContentsTheSame(
//            oldItem: CoinEntity,
//            newItem: CoinEntity
//        ): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }
}
