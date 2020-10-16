package jd.demo.se.concurrent.basic;

import java.util.concurrent.*;


public class DemoSemaphore {

    /**
     * 操场，有5个跑道
     * Created by Xingfeng on 2016-12-09.
     */
    static class Playground {

        /**
         * 跑道类
         */
        static class Track {
            private int num;

            public Track(int num) {
                this.num = num;
            }

            @Override
            public String toString() {
                return "Track{" +
                        "num=" + num +
                        '}';
            }
        }

        private Track[] tracks = {
                new Track(1), new Track(2), new Track(3), new Track(4), new Track(5)};
        private volatile boolean[] used = new boolean[5];

        private Semaphore semaphore = new Semaphore(5, true);

        /**
         * 获取一个跑道
         */
        public Track getTrack() throws InterruptedException {

            semaphore.acquire(1);
            return getNextAvailableTrack();

        }

        /**
         * 返回一个跑道
         *
         * @param track
         */
        public void releaseTrack(Track track) {
            if (makeAsUsed(track))
                semaphore.release(1);
        }

        /**
         * 遍历，找到一个没人用的跑道
         *
         * @return
         */
        private Track getNextAvailableTrack() {

            for (int i = 0; i < used.length; i++) {
                if (!used[i]) {
                    used[i] = true;
                    return tracks[i];
                }
            }

            return null;

        }

        /**
         * 返回一个跑道
         *
         * @param track
         */
        private boolean makeAsUsed(Track track) {

            for (int i = 0; i < used.length; i++) {
                if (tracks[i] == track) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    } else {
                        return false;
                    }

                }
            }

            return false;
        }

    }


    static class Student implements Runnable {

        private int num;
        private Playground playground;

        public Student(int num, Playground playground) {
            this.num = num;
            this.playground = playground;
        }

        @Override
        public void run() {

            try {
                //获取跑道
                Playground.Track track = playground.getTrack();
                if (track != null) {
                    System.out.println("学生" + num + "在" + track.toString() + "上跑步");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("学生" + num + "释放" + track.toString());
                    //释放跑道
                    playground.releaseTrack(track);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public static  void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        Playground playground = new Playground();
        for (int i = 0; i < 10000; i++) {
            executor.execute(new Student(i+1,playground));
        }
    }
}
