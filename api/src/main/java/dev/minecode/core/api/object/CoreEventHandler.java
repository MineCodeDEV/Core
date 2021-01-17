package dev.minecode.core.api.object;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CoreEventHandler {
    CoreEventPriority eventPriority() default CoreEventPriority.NORMAL;
}
