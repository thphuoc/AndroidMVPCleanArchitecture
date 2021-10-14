package com.phuoc.domain.exceptions

import java.lang.Exception

open class RemoteException(val code: Int, message: String) : Exception(message)