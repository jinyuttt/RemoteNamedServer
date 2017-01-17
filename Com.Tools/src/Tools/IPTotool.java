package Tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPTotool {
	public static int ipStr2int(String ipstr)
	{
	    byte[] bytes = null;
		try {
			bytes = InetAddress.getByName(ipstr).getAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    int addr = bytes[3] & 0xFF;
	    addr |= ((bytes[2] << 8) & 0xFF00);
	    addr |= ((bytes[1] << 16) & 0xFF0000);
	    addr |= ((bytes[0] << 24) & 0xFF000000);
	    return addr;
	}
	 public static String intToIp(int ipInt) {
	        return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.')
	                .append((ipInt >> 16) & 0xff).append('.').append(
	                        (ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff))
	                .toString();
	    }

}
