package nameServerFrame;


import java.util.concurrent.ConcurrentHashMap;

/**
 * 保存服务实例
 * @author jinyu
 *
 */
public class ServerInstances {
	public static ConcurrentHashMap<String, ServerInfo> servers = new ConcurrentHashMap<String, ServerInfo>();

}
