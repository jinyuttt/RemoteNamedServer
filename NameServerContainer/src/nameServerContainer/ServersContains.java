package nameServerContainer;



import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import serverStruct.ServerBinds;


/**
 * 服务信息存储
 * @author jinyu
 *
 */
public class ServersContains {
public static  ConcurrentHashMap<String,ArrayList<ServerBinds >> servers=new ConcurrentHashMap<String, ArrayList<ServerBinds >>();

}
