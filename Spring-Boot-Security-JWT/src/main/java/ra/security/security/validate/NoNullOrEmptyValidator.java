package ra.security.security.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NoNullOrEmptyValidator implements ConstraintValidator<NoNullOrEmpty, List<?>> {

    @Override
    public void initialize(NoNullOrEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
        if (list == null) {
            return false;
        }

        for (Object element : list) {
            if (element == null || (element instanceof String && ((String) element).trim().isEmpty())) {
                return false;
            }
        }

        return true;
    }
}
