package example.hello;

import com.fourinone.BeanContext;
import com.fourinone.pattern.Contractor;
import com.fourinone.pattern.warehouse.WareHouse;
import com.fourinone.pattern.worker.MigrantWorker;
import com.fourinone.pattern.worker.WorkerLocal;
import com.fourinone.pattern.worker.Workman;

/**
 * 假设你已经看过分布式计算上手demo指南,对fourinone基本的分布式并行计算方式有了初步了解。

 本demo演示了工头和几个工人之间互相sayhello的简单例子，从而了解到集群计算节点之间互相交互，以及工头批量处理和工人互相传递数据（多用于合并）的功能。

 HelloContractor：是一个工头实现，它实现giveTask接口，它首先通过getWaitingWorkers获取到一个线上工人的集合，然后通过doTaskBatch进行批量任务处理，这里工头向每个工人说句“hello”打招呼。doTaskBatch有两个参数，分别是工人集合和任务，该方法会等到每个工人都执行完该任务才返回，因此使用doTaskBatch不需要轮循检查每一个调用结果，它是一个批量处理。为了节省资源利用，工头运行结束后不会退出jvm，可以使用exit方法强行退出。

 HelloWorker：是一个工人实现，这里它实现了doTask和receive接口，分别用于被工头和其他工人调用。doTask实现了被工头调用执行任务的内容，这里该工人向工头和其他工人“say hello”招呼，并告诉自己的名字。它通过getWorkerElse获取到集群中除自己以外的其他工人，getWorkerElse可以传入一个参数指定工人类型，然后依次调用其他工人的receive方法传递信息。receive实现了该工人被其他工人调用的处理内容，参数WareHouse由其他工人传入，它返回一个boolean值，可以代表接收和处理是否成功。这里简单的将其他工人的问候输出。

 运行步骤：
 1、启动ParkServerDemo（它的IP端口已经在配置文件的PARK部分的SERVERS指定）
 2、运行一到多个HelloWorker（传入3个参数，依次是该工人的名字、ip或者域名、端口）
 3、运行HelloCtor

 注意：doTaskBatch会等集群中最慢的一个工人完成任务才统一返回，如果希望能让机器运行快的机器在完成后能马上又分配新的任务，而不用等待，实现能者多劳，可以不使用doTaskBatch，而采用逐个调用每个工人的doTask并轮循结果状态的方式实现，具体请参考分布式计算完整demo

 实际上，工头对工人的调用是通过doTask，工人对工人的调用是通过receive。doTask用于工头分配任务，receive多用于工人之间合并传递数据，每个工人都可以同时向其他工人传递数据，并接收来自其他工人的数据。集群中每个工人向其他工人传递数据都完成了，也就意味着每个工人都接收完成了。
 */
public class ParkServerDemo {

    static final String WORKER_TYPE = "helloworker";

    public static void main(String[] args) {
        BeanContext.startPark();
    }

    public static class HelloContractor extends Contractor {
        public static void main(String[] args) {
            HelloContractor a = new HelloContractor();
            a.giveTask(null);
            a.exit();
        }

        public WareHouse giveTask(WareHouse inhouse) {

            WorkerLocal[] wks = getWaitingWorkers(WORKER_TYPE);
            System.out.println("wks.length:" + wks.length);
            WareHouse wh = new WareHouse("word", "hello, i am your Contractor.");
            WareHouse[] hmarr = doTaskBatch(wks, wh);

            for (WareHouse result : hmarr)
                System.out.println(result);

            return null;
        }
    }
    public static class HelloWorker1{
        public static void main(String[] args) {
            HelloWorker.create("Work1",7001);
        }
    }
    public static class HelloWorker2{
        public static void main(String[] args) {
            HelloWorker.create("Work2",7002);
        }
    }
    public static class HelloWorker3{
        public static void main(String[] args) {
            HelloWorker.create("Work3",7003);
        }
    }


    public static class HelloWorker extends MigrantWorker {
        private String name;

        public HelloWorker(String name) {
            this.name = name;
        }

        public static MigrantWorker create(String name,int port) {
            HelloWorker mw = new HelloWorker(name);
            mw.waitWorking("localhost", port, WORKER_TYPE);
            return mw ;
        }

        public WareHouse doTask(WareHouse inhouse) {
            System.out.println(inhouse.getString("word"));
            WareHouse wh = new WareHouse("word", "hello, i am " + name);
            Workman[] wms = getWorkerElse(WORKER_TYPE);
            for (Workman wm : wms)
                wm.receive(wh);
            return wh;
        }

        public boolean receive(WareHouse inhouse) {
            System.out.println(inhouse.getString("word"));
            return true;
        }
    }

}