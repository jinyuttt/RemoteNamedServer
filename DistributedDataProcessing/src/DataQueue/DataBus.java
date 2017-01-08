package DataQueue;


import java.util.concurrent.LinkedBlockingQueue;


import DataModel.QueueModel;

public class DataBus {
	LinkedBlockingQueue<QueueModel> data=new LinkedBlockingQueue<QueueModel>();
 IQueueCall thecall;
 public DataBus(IQueueCall call)
 {
	 thecall=call;
	 
	 Thread dataOut=new Thread(new Runnable()
			 {

				@Override
				public void run() {
				
					while(true)
					{
						QueueModel model = null;
						try {
							model = data.take();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						if(model!=null&&thecall!=null)
						{
							thecall.OutData(model);
						}
						else if(model==null)
						{
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
							
								e.printStackTrace();
							}
						}
					}
				}
		 
			 });
	 dataOut.setDaemon(true);
	 dataOut.setName("databus");
	 dataOut.start();
 }
 public void AddData(QueueModel model)
 {
	 try {
		data.put(model);
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	}
 }
 
}
