package com.luckk.lizzie.rpc.exception;

/**
 * @FileName: LubboException
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 17:20
 */
public class LubboException extends RuntimeException {
    private String msg = "lubbo exeception";
    @Override
    public String getMessage() {
        return msg;
    }
}
