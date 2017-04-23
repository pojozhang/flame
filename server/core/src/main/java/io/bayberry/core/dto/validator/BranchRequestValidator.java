package io.bayberry.core.dto.validator;

import io.bayberry.core.dto.BranchRequest;
import io.bayberry.core.exception.Errors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class BranchRequestValidator extends Validator<BranchRequest> {

    @Override
    public boolean isValid(BranchRequest request) {
        if (StringUtils.isBlank(request.getName()))
            throw Errors.BRANCH_NAME_IS_BLANK;
        return true;
    }
}