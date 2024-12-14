package com.intern.aifortodo.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.intern.aifortodo.R
import com.intern.aifortodo.databinding.MainActivityBinding
import com.intern.aifortodo.ui.Fragment.MyBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * [AppCompatActivity] Main Activity.
 * Contains the NavHostFragment and listens the destinations changes.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private val mainViewModel by viewModels<MainViewModel>()

    // Register the permissions callback, which handles the user's response to the system permissions dialog.
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "通知权限已授予", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "通知权限被拒绝", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
        setBottomAppBar()
        requestNotificationPermission() // 在启动时请求通知权限
        binding.createButton.setOnClickListener {
            navController().navigate(R.id.addTaskFragment)
        }
        val floatingButton = findViewById<FloatingActionButton>(R.id.floatingButton)
        floatingButton.setOnClickListener {
            // 显示底部抽屉对话
            showBottomSheetDialog()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showBottomSheetDialog() {
        val dialog = MyBottomSheetDialogFragment()
        dialog.show(supportFragmentManager, "MyBottomSheetDialogFragment")
    }

    private fun setBottomAppBar() {
        binding.bottomAppBar.setNavigationOnClickListener {
            showMenu()
        }
        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.more -> {
                    showMoreOptions()
                    true
                }
                else -> false
            }
        }
    }

    private fun setNavigation() {
        navController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.tasksFragment -> {
                    binding.bottomAppBar.visibility = View.VISIBLE
                    mainViewModel.projects.observe(this) {
                        if (!it.isNullOrEmpty()) {
                            binding.createButton.show()
                        } else {
                            binding.createButton.hide()
                        }
                    }
                }
                else -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.createButton.hide()
                    mainViewModel.projects.removeObservers(this)
                }
            }
        }
    }

    private fun showMoreOptions() =
        navController().navigate(R.id.moreBottomSheetDialog)

    private fun showMenu() =
        navController().navigate(R.id.menuDialog)

    private fun navController() = findNavController(R.id.nav_host_fragment)

}
