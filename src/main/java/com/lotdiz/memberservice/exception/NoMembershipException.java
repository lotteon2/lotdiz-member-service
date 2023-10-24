package com.lotdiz.memberservice.exception;

import com.lotdiz.memberservice.utils.CustomErrorMessage;

public class NoMembershipException extends RuntimeException {
    public NoMembershipException() {
        super(CustomErrorMessage.NO_MEMBERSHIP);
    }
}
