package threads.threads_10_notify_wait_sync_method.post_office;

import java.util.LinkedList;

public class PostOffice {

    private LinkedList<String> packageQueue = new LinkedList<>();

    public String receivePostPackage(){
        return packageQueue.poll();
    }

    public void loadPostPackages(LinkedList<String> postPackeges){
        packageQueue.addAll(postPackeges);
        System.out.println(packageQueue.size());
    }

    public boolean thereAreMorePackages(){
        return packageQueue.isEmpty();
    }
}
