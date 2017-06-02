package com.management.dto;

import org.springframework.stereotype.Component;

@Component
public class ResultDtoFactory {
    
    public ResultDtoFactory() {
    }

    public static ResultDto toSuccess(String reason) {
        return toSuccess(reason, null);
    }

    public static ResultDto toSuccess(Object data) {
        return toSuccess(null, data);
    }

    public static ResultDto toSuccess(String reason, Object result) {
        ResultDto dto = new ResultDto();
        dto.setResultCode("0");
        dto.setReason(reason);
        dto.setResult(result);
        return dto;
    }
    
    public static ResultDto toUnknowError(){
    	ResultDto dto = new ResultDto();
        dto.setResultCode("-1");
        dto.setReason("UnknowError");
        dto.setResult(null);
        return dto;
    }
    
    public static ResultDto toError(ResultCode rc) {
        ResultDto dto = new ResultDto();
        dto.setResultCode(rc.getCode());
        dto.setReason(rc.getMessage());
        dto.setResult(null);
        return dto;
    }
    
}
