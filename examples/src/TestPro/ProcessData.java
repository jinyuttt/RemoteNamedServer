package TestPro;

import DataModel.QueueModel;
import DataQueue.IQueueCall;


public class ProcessData implements IQueueCall{

	 long num=0;
	@Override
	public void OutData(QueueModel model) {
		 
		num++;
		System.out.println(num);
	}

}
