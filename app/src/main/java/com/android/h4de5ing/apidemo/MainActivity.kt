package com.android.h4de5ing.apidemo

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.settingc.AdminReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var dpm: DevicePolicyManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(this, AdminReceiver::class.java)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.dpm_button).setOnClickListener {
            startActivity(Intent(this, DeviceAdminSample::class.java))
            toast("打开新页面")
        }
        val cb = findViewById<CheckBox>(R.id.dpm)
        cb.isChecked = dpm.isAdminActive(componentName)
        cb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                intent.putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    getString(R.string.add_admin_extra_app_text)
                )
                startActivityForResult(intent, 1)
            } else {
                dpm.removeActiveAdmin(componentName)
            }
        }
        findViewById<Button>(R.id.disable_camera).setOnClickListener {
            if (dpm.isDeviceOwnerApp(packageName)) {
                dpm.lockNow()
            } else {
                toast("请先成为设备管理员")
            }
        }
    }

    private fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}