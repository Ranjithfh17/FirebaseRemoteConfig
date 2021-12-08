package com.fh.firebaseremoteconfig

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fh.firebaseremoteconfig.databinding.FragmentHomeBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseConfigSettings:FirebaseRemoteConfigSettings
    private lateinit var firebaseRemoteConfig:FirebaseRemoteConfig
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        CoroutineScope(Dispatchers.Main).launch {
            firebaseConfigSettings= FirebaseRemoteConfigSettings
                .Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            firebaseRemoteConfig= FirebaseRemoteConfig.getInstance()
            firebaseRemoteConfig.setConfigSettingsAsync(firebaseConfigSettings)
            firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config)


            firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful){
                    Log.i("TAG", "onCreate: ${it.result}")
                }else{
                    Toast.makeText(requireContext(), "failed", Toast.LENGTH_LONG).show()
                }


                updateViews()



            }
        }





    }

    private fun updateViews(){

        binding.titleText.text=firebaseRemoteConfig.getString("hello")



    }

}