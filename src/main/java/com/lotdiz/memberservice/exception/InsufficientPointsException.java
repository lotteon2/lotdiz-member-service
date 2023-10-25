package com.lotdiz.memberservice.exception;

import com.lotdiz.memberservice.utils.CustomErrorMessage;

public class InsufficientPointsException extends RuntimeException {
    public InsufficientPointsException() {
        super(CustomErrorMessage.INSUFFIENT_POINTS);
    }
}
