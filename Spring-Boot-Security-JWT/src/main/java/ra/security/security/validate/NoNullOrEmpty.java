package ra.security.security.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoNullOrEmptyValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNullOrEmpty {
    String message() default "List cannot contain null or empty elements";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
