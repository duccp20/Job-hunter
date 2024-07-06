//package vn.hoidanit.jobhunter.util;
//
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
//
//@ControllerAdvice
//public class FormatApiResponse implements ResponseBodyAdvice<Object> {
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(
//            Object body,
//            MethodParameter returnType,
//            MediaType selectedContentType,
//            Class selectedConverterType,
//            ServerHttpRequest request,
//            ServerHttpResponse response) {
//
//        //ấy http code.
//        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
//        int status = servletResponse.getStatus();
//
//        ApiResponse<Object> res = new ApiResponse<>();
//        res.set(status);
//
//        if (status >= 400) {
//            //case fail
//            return body;
//        } else {
//
//            //case success
//            res.setData(body);
//
//        }
//
//        return res;
//    }
//
//}