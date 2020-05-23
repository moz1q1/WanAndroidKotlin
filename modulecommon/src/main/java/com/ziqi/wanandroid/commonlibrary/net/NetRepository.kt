package com.ziqi.wanandroid.commonlibrary.net

import com.ziqi.wanandroid.commonlibrary.bean.*
import kotlinx.coroutines.Deferred
import retrofit2.http.FieldMap

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/12 6:08 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object NetRepository {

    suspend fun banner(): WanResponse<MutableList<Banner>> {
        return RetrofitUtils.get().wanAndroidApi.banner()
    }

    suspend fun articleTop(): WanResponse<MutableList<Article>> {
        return RetrofitUtils.get().wanAndroidApi.articleTop()
    }

    suspend fun articleList(pos: Int): WanResponse<WanList<Article>> {
        return RetrofitUtils.get().wanAndroidApi.articleList(pos)
    }

    suspend fun listproject(pos: Int): WanResponse<ListProject> {
        return RetrofitUtils.get().wanAndroidApi.listproject(pos)
    }

    suspend fun tree(): WanResponse<MutableList<Tree>> {
        return RetrofitUtils.get().wanAndroidApi.tree()
    }

    suspend fun articleList(pos: Int, cid: Int): WanResponse<WanList<Article>> {
        return RetrofitUtils.get().wanAndroidApi.articleList(pos, cid)
    }

    suspend fun projectTree(): WanResponse<MutableList<Tree>> {
        return RetrofitUtils.get().wanAndroidApi.projectTree()
    }

    suspend fun project(pos: Int, cid: Int): WanResponse<ListProject> {
        return RetrofitUtils.get().wanAndroidApi.project(pos, cid)
    }

    suspend fun wxArticleChapters(): WanResponse<MutableList<Tree>> {
        return RetrofitUtils.get().wanAndroidApi.wxArticleChapters()
    }

    suspend fun wxArticleList(pos: Int, cid: Int): WanResponse<ListProject> {
        return RetrofitUtils.get().wanAndroidApi.wxArticleList(cid, pos)
    }

    suspend fun login(@FieldMap map: Map<String, String>): WanResponse<User> {
        return RetrofitUtils.get().wanAndroidApi.login(map)
    }
}