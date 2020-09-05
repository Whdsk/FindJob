package com.example.lichengnan.ui.activity

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import com.example.lichengnan.BaseActivity
import com.example.lichengnan.R
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsReaderView
import com.tencent.smtt.sdk.ValueCallback
import org.json.JSONException
import org.json.JSONObject
import java.io.File

/**
 * @author：gaohangbo on 2020/9/3 16:08
 */

class PdfReaderActivity : BaseActivity() , TbsReaderView.ReaderCallback, ValueCallback<String> {
     lateinit var mTbsReaderView : TbsReaderView
     var mDownloadManager: DownloadManager? = null
     lateinit var mDownloadBtn:Button
     lateinit var open_file:Button
     var mRequestId: Long = 0
     var mDownloadObserver: DownloadObserver? = null
     val mFileUrl = Environment.getExternalStorageDirectory().path+"/正在直播的课程.pdf"
     //val mFileUrl = "file:///android_assets/downcc.pdf"
     var mFileName: String? = null

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfreader)
        mDownloadBtn = findViewById(R.id.btn_download)
        open_file = findViewById(R.id.open_file)
        val rootRl = findViewById<View>(R.id.rl_root) as RelativeLayout
         mTbsReaderView= TbsReaderView(this@PdfReaderActivity,readerCallback)
         rootRl.addView(mTbsReaderView, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT))
         Log.e("ddddddwwfff",mFileUrl+"ddddd")
         mFileName = parseName(mFileUrl)
         mFileName?.let { Log.e("dddddeeee", it) }
         if (isLocalExist()) {
             mDownloadBtn.text = "打开文件"
         }
//         try {
//             val fileNames: Array<String> = context.getAssets().list(oldPath) //获取assets目录下的所有文件及目录名
//             if (fileNames.size > 0) { //如果是目录
//                 val file = File(newPath)
//                 file.mkdirs() //如果文件夹不存在，则递归
//                 for (fileName in fileNames) {
//                     copyFilesFassets(context, oldPath.toString() + File.separator + fileName, newPath.toString() + File.separator + fileName)
//                 }
//             } else { //如果是文件
//                 val `is`: InputStream = context.getAssets().open(oldPath)
//                 val fos = FileOutputStream(File(newPath))
//                 val buffer = ByteArray(1024)
//                 var byteCount = 0
//                 while (`is`.read(buffer).also({ byteCount = it }) != -1) { //循环从输入流读取 buffer字节
//                     fos.write(buffer, 0, byteCount) //将读取的输入流写入到输出流
//                 }
//                 fos.flush() //刷新缓冲区
//                 `is`.close()
//                 fos.close()
//             }
//         } catch (e: Exception) {
//             // TODO Auto-generated catch block
//             e.printStackTrace()
//             //如果捕捉到错误则通知UI线程
//             //MainActivity.handler.sendEmptyMessage(COPY_FALSE)
//         }
         //val mAssets: InputStream = assets.open("name.txt")
         try {
             var fileNames: Array<String>
             //获取assets/跟目录下的所有文件和文件夹
             fileNames = this@PdfReaderActivity.assets.list("") as Array<String>
             if (fileNames.isNotEmpty()) { //如果是目录
                 for(fileName in fileNames){
                     Log.e("edewee",fileName)
                 }
//                 val file = File(newPath)
//                 file.mkdirs() //如果文件夹不存在，则递归
//                 for (fileName in fileNames) {
//                     copyFilesFassets(context, oldPath.toString() + File.separator + fileName, newPath.toString() + File.separator + fileName)
//                 }
             }
         }catch (e: Exception){
             e.printStackTrace()
             Log.e("ddddddwwfff111",e.message.toString())
         }

    }

    // 回调
    var readerCallback = TbsReaderView.ReaderCallback { integer, o, o1 -> }


    //开始下载，点击事件
    fun onClickDownload(v: View?) {
        if (isLocalExist()) {
            mDownloadBtn.visibility = View.GONE
            displayFile()
        } else {
            startDownload()
        }
    }

    //
    fun onOpenFile(v: View?) {
        val bundle = Bundle()
        bundle.putString("filePath", mFileUrl)
        bundle.putString("tempPath", mFileUrl)
        val result = mTbsReaderView.preOpen(parseFormat(mFileName!!), false)
        if (result) {
            mTbsReaderView.openFile(bundle)
        }

        //val docName: String = docUrl.substring(i, docUrl.length())
        //Log.d("print", "---substring---$docName")

//        String[] split = docUrl.split("\\/");
//        String s = split[split.length - 4] + split[split.length - 3] + split[split.length - 2] + split[split.length - 1];
//        Log.d("print", "截取带时间---" + s);
        //判断是否在本地/[下载/直接打开]

//        String[] split = docUrl.split("\\/");
//        String s = split[split.length - 4] + split[split.length - 3] + split[split.length - 2] + split[split.length - 1];
//        Log.d("print", "截取带时间---" + s);
        //判断是否在本地/[下载/直接打开]
        //val docFile = File(download, docName)
    }

    private fun displayFile() {
//        val bundle = Bundle()
//        bundle.putString("filePath", getLocalFile().path)
//        bundle.putString("tempPath", Environment.getExternalStorageDirectory().path)
//        val result = mTbsReaderView.preOpen(parseFormat(mFileName!!), false)
//        if (result) {
//            mTbsReaderView.openFile(bundle)
//        }
        val Object = JSONObject()
        try {
            Object.put("pkgName", this@PdfReaderActivity.application.packageName)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val params =  HashMap<String, String>();
        params["style"] = "1";
        params["local"] = "true";
        params["memuData"] = Object.toString();
        //QbSdk.openFileReader(ctx,”/sdcard/xxx.doc”, params,callback);
        QbSdk.openFileReader(this@PdfReaderActivity, "file:///android_asset/downcc.pdf", params, this)
    }


    private fun parseFormat(fileName: String): String? {
        return fileName.substring(fileName.lastIndexOf(".") + 1)
    }

    private fun parseName(url: String): String? {
        var fileName: String? = null
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1)
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = System.currentTimeMillis().toString()
            }
        }
        return fileName
    }

    private fun isLocalExist(): Boolean {
        return getLocalFile().exists()
    }

    private fun getLocalFile(): File {
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mFileName)
    }

    private fun startDownload() {
        mDownloadObserver = DownloadObserver(Handler())
        contentResolver.registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, mDownloadObserver!!)
        mDownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(mFileUrl))
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mFileName)
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
        mRequestId = mDownloadManager!!.enqueue(request)
    }

    //内部类
    inner class DownloadObserver(handler: Handler) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            Log.i("downloadUpdate: ", "onChange(boolean selfChange, Uri uri)")
            queryDownloadStatus()
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, PdfReaderActivity::class.java))
        }
    }

    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mTbsReaderView.onStop()
        if (mDownloadObserver != null) {
            contentResolver.unregisterContentObserver(mDownloadObserver!!)
        }
    }

     fun queryDownloadStatus() {
        val query = DownloadManager.Query().setFilterById(mRequestId)
        var cursor: Cursor? = null
        try {
            cursor = mDownloadManager!!.query(query)
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载的字节数
                val currentBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                //总需下载的字节数
                val totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                //状态所在的列索引
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                Log.i("downloadUpdate: ", "$currentBytes $totalBytes $status")
                mDownloadBtn.text = "正在下载：$currentBytes/$totalBytes"
                if (DownloadManager.STATUS_SUCCESSFUL == status && mDownloadBtn.visibility == View.VISIBLE) {
                    mDownloadBtn.visibility = View.GONE
                    mDownloadBtn.performClick()
                }
            }
        } finally {
            cursor?.close()
        }
    }

    override fun onReceiveValue(p0: String?) {

    }


}