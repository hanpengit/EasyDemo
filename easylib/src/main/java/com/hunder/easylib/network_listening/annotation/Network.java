package com.hunder.easylib.network_listening.annotation;

import com.hunder.easylib.network_listening.type.NetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hp on 2019/12/5.
 */

@Target(ElementType.METHOD) // 作用在方法上
@Retention(RetentionPolicy.RUNTIME) // jvm运行时，通过反射获取该注解的值
public @interface Network {
    NetType netType() default NetType.TYPE_ALL;
}
