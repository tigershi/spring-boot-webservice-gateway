package org.springframework.cloud.ws.ui.annotation;


import java.lang.annotation.*;

/**
 * Author:Tiger Shi
 * Date:2024/12/4
 * 此接口用于webservice module 定义
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface WSModuleDesc {

    String name() default "Default";

    String desc() default "";

}
