package com.green.namu.common.exceptions;

import com.green.namu.common.response.BaseResponse;
import com.green.namu.common.response.BaseResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestControllerAdvice // RESTful 응답 반환
public class ExceptionAdvice {

    public BaseResponse<BaseResponseStatus> BaseExceptionHandle(BaseException exception) {
        log.warn("BaseException. error message: {}", exception);
        return new BaseResponse<>(exception.getStatus());
    }

    public BaseResponse<BaseResponseStatus> ExceptionHandle(Exception exception) {
        log.error("Exception has occured. ", exception);
        return new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
    }
}
