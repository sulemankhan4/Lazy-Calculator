package com.example.developer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData

class SplashActivity : AppCompatActivity() {

    lateinit var redirectionIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkForDynamicLinks()
    }


    private fun startNextActivity() {
        if (::redirectionIntent.isInitialized) {
            //Intent has been initialized with deeplinking
            startActivity(redirectionIntent)
        } else {
            //Handle normal flow of app.

            startActivity(Intent(this, DetailActivity::class.java))

        }
    }

    private fun checkForDynamicLinks() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent).addOnSuccessListener(this) { pendingDynamicLinkData ->
                pendingDynamicLinkData?.let {
                    /**
                     * Web Link url : For example https://lazycalculators.page.link/add
                     * Deeplink url : https://lazycalculators.page.link/?page=operation&type=add


                     */
                    //name of screen.
                    val page = it.link?.getQueryParameter("page").orEmpty()
                    if (!TextUtils.isEmpty(page)) {
                        //addition or subtract or division or multiplication
                        val operationType = it.link?.getQueryParameter("operation_type").orEmpty()
                        if (page == "operation") {
                            redirectionIntent = Intent(this, OperationActivity::class.java)
                            redirectionIntent.putExtra("operation_type", operationType)
                        }
                    }
                    startNextActivity()
                } ?: let {
                    startNextActivity()
                }
            }
            .addOnFailureListener(this) { e ->
                Log.w("", "getDynamicLink:onFailure", e);
                startNextActivity()
            }

    }

}