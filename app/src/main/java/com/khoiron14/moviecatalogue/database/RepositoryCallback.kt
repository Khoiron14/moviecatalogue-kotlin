package com.khoiron14.moviecatalogue.database

/**
 * Created by khoiron14 on 9/5/2019.
 */
interface RepositoryCallback<D> {
    fun onDataSuccess(base: D)
    fun onDataError(message: String?)
}