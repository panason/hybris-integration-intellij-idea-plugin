package com.intellij.idea.plugin.hybris.tools.remote.http

import com.intellij.idea.plugin.hybris.tools.remote.http.impex.HybrisHttpResult
import com.intellij.idea.plugin.hybris.tools.remote.http.solr.SolrQueryObject
import com.intellij.openapi.project.Project

/**
 * @author Nosov Aleksandr <nosovae.dev@gmail.com>
 */
interface SolrHttpClient {
    fun listOfCores(project: Project): Array<String>
    fun executeSolrQuery(project: Project, queryObject: SolrQueryObject): HybrisHttpResult

    companion object {
        @JvmStatic
        fun getInstance(project: Project): SolrHttpClient {
            return project.getComponent(SolrHttpClient::class.java)
        }
    }
}