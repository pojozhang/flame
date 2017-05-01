package io.bayberry.core.controller.advice;

import io.bayberry.core.common.util.RequestUtils;
import io.bayberry.core.dto.ApiRequest;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class ApiRequestBodyAdvice extends GenericRequestBodyAdvice<ApiRequest> {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public ApiRequest postProcess(ApiRequest request, MethodParameter parameter) {
        Map<String, String> pathVariables = RequestUtils.getPathVariables(httpServletRequest);
        request.setBranchId(MapUtils.getLong(pathVariables, "branchId"));
        request.setModuleId(MapUtils.getLong(pathVariables, "moduleId"));
        return request;
    }
}
