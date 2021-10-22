package com.phuoc.domain.exceptions

class InvalidAccessTokenException : RemoteException(code = 401, message = "Invalid Credentials")