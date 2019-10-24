/**   
* @Title: ExceptionAdvice.java 
* @Package com.brt.policeMonitor.rest.exception 
* @Description: TODO(用一句话描述该文件做什么) 
* @author wangmeilan   
* @date 2016年8月22日 下午3:30:42 
* @version V1.0   
*/
package com.brt.manager.excption;

import com.brt.manager.rest.vo.ResEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {


    /**
     * 802 - Bad Request
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBusinessException.class)
    public ResEntity handlePoliceCodeNotUniqueException(HttpServletResponse re, CustomBusinessException e) {
//    	log.error("自定义业务逻辑异常");
    	re.setStatus(801);
        return new ResEntity().failure(e.getLocalizedMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ResEntity handleJavaLangException(HttpServletResponse re, CustomBusinessException e) {
//    	log.error("自定义业务逻辑异常");
    	re.setStatus(802);
        return new ResEntity().failure(e.getLocalizedMessage());
    }
    
    
}
