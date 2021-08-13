package com.example.test.data.apimgr.exceptions

class InvalidAccessTokenException : RemoteException(code = 401, "Invalid token")