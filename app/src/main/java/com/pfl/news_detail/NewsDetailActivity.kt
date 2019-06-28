package com.pfl.news_detail

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import com.pfl.geeknews.InfoBean
import com.pfl.geeknews.R
import com.pfl.geeknews.RvAdapter
import com.pfl.lib_common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : BaseActivity() {

    override fun getContentView() = R.layout.activity_news_detail

    override fun isImmersive() = false

    override fun isDrakMode() = false

    override fun initView() {
        initWebView()
        initRecyclerView()
    }

    private fun initWebView() {
        webContainer.settings.javaScriptEnabled = true
        webContainer.webViewClient = WebViewClient()
        webContainer.webChromeClient = WebChromeClient()
        webContainer.loadUrl("https://github.com/wangzhengyi/Android-NestedDetail")
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rvList.layoutManager = layoutManager
        val data = getCommentData()
        val rvAdapter = RvAdapter()
        rvList.adapter = rvAdapter
        rvAdapter.setNewData(data)
    }

    private fun getCommentData(): List<InfoBean> {
        val commentList = ArrayList<InfoBean>()
        val titleBean = InfoBean()
        titleBean.type = InfoBean.TYPE_TITLE
        titleBean.title = "评论列表"
        commentList.add(titleBean)
        for (i in 0..39) {
            val contentBean = InfoBean()
            contentBean.type = InfoBean.TYPE_ITEM
            contentBean.title = "评论标题$i"
            contentBean.content = "评论内容$i"
            commentList.add(contentBean)
        }
        return commentList
    }
}
