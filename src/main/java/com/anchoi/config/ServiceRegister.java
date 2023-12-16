//package com.anchoi.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ServiceRegister {
//    private final Logger logger = LoggerFactory.getLogger(ServiceRegister.class);
//
//    @Autowired
//    private ApplicationContext ctx;
//
//    public <I, O> ResponseEntity<O> processAuthenType(String authenType, ResponseEntity<I> req) {
//        try {
//            if (processors.containsKey(authenType) || req.getBody() == null) {
//                logger.debug("Execute method AuthenType: {}", authenType);
//                ApiBody<I> body = req.getBody();
//                Object data = body.getData();
//
//                if (data == null) {
//                    // Message khong co tham so data
//                    return (ApiResponse<O>) processors.get(authenType).execute();
//                } else {
//                    return (ApiResponse<O>) processors.get(authenType).execute(data);
//                }
//            } else {
//                logger.debug("{}{}-authenType invalid", logUtils.getPrefixLog(req), authenType);
//                ApiError err = apiErrorUtils.getError(ApiFwConstants.ErrorCode.INVALID_AUTHEN_TYPE);
//                ApiResponse<O> dataResponse = new ApiResponse<>();
//                dataResponse.setHeader(req.getHeader());
//                dataResponse.setError(err);
//                return dataResponse;
//            }
//        } catch (Exception ex) {
//            ResponseEntity<O> dataResponse = new ResponseEntity<>();
//
//            if (ex.getCause() instanceof BusinessException) {
//                // Throw exception nghiep vu
//                BusinessException businessException = (BusinessException) ex.getCause();
//                ApiError apiError = apiErrorUtils.getError(businessException.getCode(),
//                        new String[]{businessException.getDesc()});
//                dataResponse.setHeader(req.getHeader());
//                dataResponse.setError(apiError);
//            } else {
//                // Exception khac
//                logger.debug("{}{}", logUtils.getPrefixLog(req), ex);
//                dataResponse.setHeader(req.getHeader());
//                dataResponse.setError(apiErrorUtils.getError(ApiFwConstants.ErrorCode.INTERNAL_API_ERR_999, new String[]{ex.toString()}));
//
//            }
//            return dataResponse;
//        }
//    }
//}
