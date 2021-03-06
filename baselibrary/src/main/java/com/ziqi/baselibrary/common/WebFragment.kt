package com.ziqi.baselibrary.common

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.ziqi.baselibrary.R
import com.ziqi.baselibrary.base.ZBaseFragment
import com.ziqi.baselibrary.databinding.FragmentWebBinding
import com.ziqi.baselibrary.util.ShareUtil
import com.ziqi.baselibrary.util.StringUtil
import com.ziqi.baselibrary.view.status.ZStatusViewBuilder
import com.ziqi.baselibrary.view.webview.SimulationListener

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 2:11 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class WebFragment : ZBaseFragment<WebInfo, FragmentWebBinding>() {
    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): WebFragment {
            var mWBaseFragment = WebFragment()
            mWBaseFragment.arguments = bundle
            return mWBaseFragment
        }
    }

    override fun onClick(v: View?) {
    }

    override fun zVisibleToUser(isNewIntent: Boolean) {
        if (mStartParams == null) {
            activity?.finish()
            return
        }
        mZStatusView?.config(
            ZStatusViewBuilder.Builder()
                .setOnErrorRetryClickListener {
                    zStatusContentView()
                    mViewDataBinding?.touchView?.loadURL(mViewDataBinding?.touchView?.getReloadURL()!!)
                }
                .build()
        )
        zStatusContentView()
        mViewDataBinding?.touchView?.setSimulationListener(object : SimulationListener {
            override fun doSimulation() {
            }

            override fun onPageFinished(url: String?) {
                mViewDataBinding?.progress?.progress = 100
                mViewDataBinding?.progress?.visibility = View.GONE
            }

            override fun onProgressChanged(newProgress: Int) {
                if (mViewDataBinding?.progress?.visibility == View.GONE) {
                    mViewDataBinding?.progress?.visibility = View.VISIBLE
                }
                mViewDataBinding?.progress?.progress = newProgress

                mLeftMenu?.visibility =
                    if (mViewDataBinding?.touchView?.canGoBack() == true) View.VISIBLE else View.GONE
            }

            override fun onReceivedTitle(title: String?) {
                mTvTitle?.text = title
            }

            override fun onError(url: String?) {
                mViewDataBinding?.progress?.progress = 100
                mViewDataBinding?.progress?.visibility = View.GONE
                zStatusErrorView()
            }

        })
        mLeftMenu?.text = "关闭"
        mLeftMenu?.setOnClickListener {
            activity?.finish()
        }
        mRightTwoMenu?.visibility = View.VISIBLE
        mRightTwoMenu?.text = "分享"
        mRightTwoMenu?.setOnClickListener {
            activity?.apply {
                ShareUtil.shareText(
                    this,
                    packageName,
                    mViewDataBinding?.touchView?.url ?: ""
                )
            }
        }
        mStartParams?.apply {
            if (!StringUtil.isEmpty(url)) {
                mViewDataBinding?.touchView?.loadURL(url)
            } else {
                mViewDataBinding?.touchView?.loadDataWithBaseURL(
                    null,
                    webContent,
                    "text/html",
                    "utf-8",
                    null
                )
            }
            mViewDataBinding?.progress?.visibility = View.VISIBLE
        }
    }

    override fun zSetLayoutId(): Int {
        return R.layout.fragment_web
    }

    override fun zContentViewId(): Int {
        return R.id.myRootView
    }

    override fun onInterceptBackPressed(): Boolean {
        if (activity == null) {
            return super.onInterceptBackPressed()
        }
        if (mViewDataBinding?.touchView?.canGoBack()!!) {
            mViewDataBinding?.touchView?.goBack()
        } else {
            activity?.finish()
        }
        return true
    }

    override fun onDestroy() {
        try {
            if (mViewDataBinding?.touchView != null) {
                val parent = mViewDataBinding?.touchView?.getParent() as ViewGroup
                parent.removeView(mViewDataBinding?.touchView)
                mViewDataBinding?.touchView?.removeAllViews()
                mViewDataBinding?.touchView?.stopLoading()
                mViewDataBinding?.touchView?.destroy()
            }
        } catch (e: Exception) {

        }
        super.onDestroy()
    }
}