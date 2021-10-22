package com.phuoc.domain.exceptions

open class RemoteException(val code: Int = 0, var title: String = "", message: String) :
    Exception(message)