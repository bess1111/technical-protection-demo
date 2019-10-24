/**   
* @Title: PoliceCodeNotUniqueException.java 
* @Package com.brt.policeMonitor.rest.exception 
* @Description:
* @author wangmeilan   
* @date 2016年8月22日 上午10:17:57 
* @version V1.0   
*/
package com.brt.manager.excption;



public class CustomBusinessException extends RuntimeException{

	private static final long serialVersionUID = 8745024787809638608L;

	public CustomBusinessException(String info){
		super(info);
	}

}
