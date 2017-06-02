package com.management.dto;

public class ResultDto {

    /**
     * 结果编码
     */
    private String resultCode;

    /**
     * 返回说明
     */
    private String reason;

    /**
     * 返回数据集
     */
    private Object result;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
