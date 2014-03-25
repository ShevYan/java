package com.shev.zootest;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

public class ZooTest {
	private ZooKeeper zk = null;
	private Watcher w = null;
	
	public ZooTest() {
		super();
		w = new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Event: " + event);
	
				if (event.getType().equals(EventType.NodeDataChanged)) {
					byte[] data = null;
					try {
						data = zk.getData("/root", true, null);
					} catch (KeeperException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String str = new String(data);
					System.out.println(event.getPath() + " changed: " + str);
				}
			}
		};
		
		try {
			zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 50000, w);
			try {
				zk.getData("/root", true, null);
			} catch (KeeperException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("waiting...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws Exception {
		ZooTest zt = new ZooTest();
	
		Thread.sleep(999999);
		return ;
	}
}
