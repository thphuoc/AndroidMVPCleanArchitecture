package com.phuoc.domain.exceptions

class InvalidAccessTokenException : RemoteException(code = 401, "Invalid token")