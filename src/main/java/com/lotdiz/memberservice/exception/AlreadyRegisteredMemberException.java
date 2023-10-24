package com.lotdiz.memberservice.exception;

import com.lotdiz.memberservice.utils.CustomErrorMessage;

public class AlreadyRegisteredMemberException extends RuntimeException {
    public AlreadyRegisteredMemberException() {
        super(CustomErrorMessage.ALREADY_REGISTERED);
    }
}
