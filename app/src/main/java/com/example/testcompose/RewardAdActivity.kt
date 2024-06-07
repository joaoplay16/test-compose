package com.example.testcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardAdActivity : ComponentActivity() {
    private val TAG = "MYADMOB"
    private var mRewardedAd: RewardedAd? by mutableStateOf(null)
    private var rewardAmount : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                RewardedAdScreen()
            }
        }
        MobileAds.initialize(this)

    }

    @Composable
    fun RewardedAdScreen() {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Coins $rewardAmount", fontSize = 26.sp)
            Spacer(Modifier.padding(32.dp))
            Button(
                onClick = {
                    showRewardedAd()
                },
                enabled = mRewardedAd != null,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                )
            ) {
                Text(text = "Show Rewarded Ad")
            }

        }
    }

    //loading the ad before show
    //when the ad is closed the onResume is called and then the ad is loaded again
    override fun onResume() {
        super.onResume()

        loadRewardedAd()
    }

    private fun loadRewardedAd(){

        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mRewardedAd = rewardedAd
                }
            })
    }

    private fun showRewardedAd(){
        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mRewardedAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mRewardedAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }

        if (mRewardedAd != null) {
            mRewardedAd?.show(this, object: OnUserEarnedRewardListener {

                override fun onUserEarnedReward(rewardItem: RewardItem) {
                    rewardAmount += rewardItem.amount
                    var rewardType = rewardItem.type
                    Log.d(TAG, "User earned the reward. amount $rewardAmount type $rewardType")
                }

            })
        } else {
            Log.d(TAG, "The reward ad wasn't ready yet.")
        }
    }
}