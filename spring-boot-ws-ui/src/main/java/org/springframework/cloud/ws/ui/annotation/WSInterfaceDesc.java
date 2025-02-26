package org.springframework.cloud.ws.ui.annotation;

import java.lang.annotation.*;

/**
 * 此注解用于描述webservice 接口，可用在webservice 注册endpoint的时候使用，
 * 或者在定义实现webservice的类上使用（有webservice注解的实现类）
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface WSInterfaceDesc {
     String name() default "";
     String webServiceUri() default "";
     String desc() default "";
     String lastUpdateTime() default "";
     String version() default "";
     String moduleName() default "";
}
