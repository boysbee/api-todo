package com.exam.todo.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
@XmlRootElement
public class RestErrorInfo {
    public final String detail;
    public final String message;

    public RestErrorInfo(Exception ex, String detail) {
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
    }
}
