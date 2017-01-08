package nameServerFrame;

public class AnalysisConnection {
	/**
	 * 解析绑定IP,端口，传输协议（TCP,UDP)
	 * @param str
	 * @return
	 */
	static Sever_BindsInfo Aysy(String str) {
		Sever_BindsInfo info = new Sever_BindsInfo();
		String[] temp = str.split(":");
		StringBuilder strBin = new StringBuilder();
		StringBuilder host = new StringBuilder();
		StringBuilder port = new StringBuilder();
		StringBuilder ttype = new StringBuilder();
		StringBuilder strconnect = new StringBuilder();
		// 名称中含有
		if (temp.length > 1) {
			for (String name : temp) {
			
				if (name.trim().startsWith("-h") || name.trim().startsWith("-p")||name.trim().startsWith("-t")) {
					//
					strconnect.append(name);
				}
				else
				{
					strBin.append(name);
				}
			}
		} else {
			strBin.append(temp[0]);
		}
		//
		temp = strconnect.toString().split(" ");
		for (int i = 0; i < temp.length; i++) {
			String strTemp = temp[i];
			if (strTemp.trim().toLowerCase().equals("-h")) {
				if (i < temp.length - 1) {
					host.append(temp[i + 1]);
				}
			}
			if (strTemp.trim().toLowerCase().equals("-p")) {
				if (i < temp.length - 1) {
					port.append(temp[i + 1]);
				}
			}
			if (strTemp.trim().toLowerCase().equals("-t")) {
				if (i < temp.length - 1) {
					ttype.append(temp[i + 1]);
				}
			}
		}
		//
		info.address = host.toString();
		info.port = Integer.valueOf(port.toString());
		info.name = strBin.toString();
		info.t_type=ttype.toString();
		return info;
	}
}
