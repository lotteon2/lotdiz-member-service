package com.lotdiz.memberservice.exception.common;

import javax.servlet.http.HttpServletResponse;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String msg) {
        super(msg);
    }

    @Override
    public int getStatusCode() {
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
