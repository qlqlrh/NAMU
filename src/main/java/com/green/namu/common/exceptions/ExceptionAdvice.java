package com.green.namu.common.exceptions;

import com.green.namu.common.response.BaseResponse;
import com.green.namu.common.response.BaseResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestControllerAdvice // RESTful 응답 반환
public class ExceptionAdvice {

    @ExceptionHandler(NoHandlerFoundException.class) // Not Found 처리
    public BaseResponse<BaseResponseStatus> NotFoundHandle(NoHandlerFoundException exception) {
        return new BaseResponse<>(BaseResponseStatus.NOT_FOUND);
    }

    @ExceptionHandler(BaseException.class) // BaseException 처리
    public BaseResponse<BaseResponseStatus> BaseExceptionHandle(BaseException exception) {
        log.warn("BaseException error message: {}", exception.getMessage(), exception);
        return new BaseResponse<>(exception.getStatus());
    }

    @ExceptionHandler(Exception.class) // 다른 모든 예외 처리
    public BaseResponse<BaseResponseStatus> ExceptionHandle(Exception exception) {
        log.error("Exception occured. ", exception);
        return new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
    }
}
