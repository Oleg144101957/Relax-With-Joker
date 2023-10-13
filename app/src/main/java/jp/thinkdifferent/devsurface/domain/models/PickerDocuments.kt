package jp.thinkdifferent.devsurface.domain.models

import android.net.Uri
import android.webkit.ValueCallback

interface PickerDocuments {
    fun onPickDoc(docs: ValueCallback<Array<Uri>>?)

}