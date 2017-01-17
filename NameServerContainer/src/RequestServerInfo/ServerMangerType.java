  
/**    
* 文件名：ServerMangerType.java    
*    
* 版本信息：    
* 日期：2017年1月17日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package RequestServerInfo;

  
/**    
*     
* 项目名称：NameServerFrame    
* 类名称：ServerMangerType    
* 类描述：    客户端到服务端处理类型
* 创建人：jinyu    
* 创建时间：2017年1月17日 下午8:47:11    
* 修改人：jinyu    
* 修改时间：2017年1月17日 下午8:47:11    
* 修改备注：    
* @version     
*     
*/
public enum ServerMangerType {
	
	/*
	 * 客户端接受服务端注册，直接开启连接访问
	 */
	ClientDirectMode, //客户端接受服务端注册，直接开启连接访问
	/*
	 * 客户端将强求发送给服务端管理，服务端直接访问服务转发数据请求
	 */
	ServerDirectMode, //客户端将强求发送给服务端管理，服务端直接访问服务转发数据请求
	/*
	 * 客户端发送请求给服务端，服务端获取当前服务的访问地址传给客户端，客户端开启连接并访问
	 */
	ServerIndirectMode//客户端发送请求给服务端，服务端获取当前服务的访问地址传给客户端，客户端开启连接并访问
}
