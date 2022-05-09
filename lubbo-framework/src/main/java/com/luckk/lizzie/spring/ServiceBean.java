package com.luckk.lizzie.spring;

import lombok.*;

/**
 * @FileName: ServiceBean
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 11:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceBean {
    private String version;
    private String group;
    private String timeout;

    private Object serviceRef;


    /**
     * 其实应该拿到它对应的接口呀
     * @return
     */
    public String getServiceName(){
        //return  serviceRef.getClass()
        return this.serviceRef.getClass().getInterfaces()[0].getCanonicalName();
    }




}
