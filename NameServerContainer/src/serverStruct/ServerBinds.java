package serverStruct;

/**
 * 服务注册信息,中间信息
 * 相比Sever_BindsInfo
 * 向客户端发送的信息
 * 没有sid,communicationType
 * @author jinyu
 *
 */
public class ServerBinds {
	
  /**
 * 服务地址
 */
public String address;

  /**
 * 服务端口
 */
public int port;

  /**
 * 服务名称
 */
public String name;

  /**
 * 启用的通信协议（TCP,UDP）,暂时无用
 */
public String communicationType;

  /**
 * 服务唯一值，用于客户端标已经接受过的信息
 */
public String sid;
}
