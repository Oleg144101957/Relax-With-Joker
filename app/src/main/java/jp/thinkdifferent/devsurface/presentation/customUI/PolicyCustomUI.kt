package jp.thinkdifferent.devsurface.presentation.customUI

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import jp.thinkdifferent.devsurface.App
import jp.thinkdifferent.devsurface.RelaxConst
import jp.thinkdifferent.devsurface.domain.models.PickerDocuments
import jp.thinkdifferent.devsurface.presentation.viewmodels.ViewModelRelax

@SuppressLint("ViewConstructor")
class PolicyCustomUI(context: Context, val pickerDocuments: PickerDocuments) : WebView(context) {

    private val contentForLauncher = "image/*"
    fun startPolicyUI(activityLauncher: ActivityResultLauncher<String>, vm: ViewModelRelax){
        configPolicyUI(settings = settings)
        webChromeClient = getWCC(activityLauncher) as WebChromeClient
        webViewClient = getWVC(vm) as WebViewClient
    }

    private fun getWVC(vm: ViewModelRelax) : Any {
        return object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()

                if (url != null){
                    analiseUrl(url, vm)
                }
            }
        }
    }

    private fun analiseUrl(url: String, vm: ViewModelRelax) {
        //check link
        //save link logic
        val washington = RelaxConst.storage1[2]+ RelaxConst.storage1[3]+ RelaxConst.storage2[2]+ RelaxConst.storage2[3]+"privacypolicy/"

        if (url == washington){
            //save WARNING and Navigate to Menu
            vm.saveDestinationInToTheStorage(RelaxConst.storage2[1])
        } else {
            //just save link
            val currentDest = vm.readDestinationFromTheStorage()

            if (currentDest.startsWith("https://fir") || currentDest == RelaxConst.storage1[1]){
                //save
                vm.saveDestinationInToTheStorage(url)
            }
        }
    }

    private fun getWCC(launcher: ActivityResultLauncher<String>) : Any {
        return object : WebChromeClient(){
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                pickerDocuments.onPickDoc(filePathCallback)
                launcher.launch(contentForLauncher)
                return true
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configPolicyUI(settings: WebSettings){
        with(settings){
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = false
            userAgentString = System.getProperty("http.agent")
                    ?.plus(userAgentString.replace("; wv", "", false))
        }
    }
}