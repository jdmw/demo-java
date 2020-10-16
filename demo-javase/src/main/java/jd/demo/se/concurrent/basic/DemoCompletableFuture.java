package jd.demo.se.concurrent.basic;

import static org.junit.Assert.*;

import jd.util.lang.concurrent.CcUt;
import jd.util.lang.concurrent.ThreadPoolUtil;
import jd.util.lang.time.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * demo java.util.concurrent.CompletableFuture
 * reference:
 * api: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html
 * ibm： https://www.ibm.com/developerworks/cn/java/j-cf-of-jdk8/index.html
 */
public class DemoCompletableFuture {

    @Test
    public void demoCompletedFuture(){
        CompletableFuture<Date>  future = CompletableFuture.completedFuture(new Date());
        Assert.assertTrue(future.isDone());
        Assert.assertNotNull(future.getNow(null));
    }

    @Test
    public void demoRunAsync() throws InterruptedException {
        CompletableFuture  future = CompletableFuture.runAsync(()->CcUt.sleepS(1));
        Assert.assertFalse(future.isDone());
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(future.isDone());
    }


    /* 同步执行 */
    @Test
    public void demoThenApply(){
        CompletableFuture<String>  future = CompletableFuture.completedFuture(new Date()).thenApply(d -> {
            Assert.assertFalse(Thread.currentThread().isDaemon());
            System.out.println("thenApply thread:" + Thread.currentThread().getName());
            return DateUtil.format(d);
        });
        System.out.println("junit method thread:" + Thread.currentThread().getName());
        Assert.assertTrue(future.isDone());
        System.out.println("thenApply result:" + future.getNow(null));
    }

    /* 异步执行 */
    @Test
    public void demoThenApplyAsync(){
        CompletableFuture<String>  future = CompletableFuture.completedFuture(new Date()).thenApplyAsync(d -> {
            Assert.assertTrue(Thread.currentThread().isDaemon());
            System.out.println("thenApplyAsync thread:" + Thread.currentThread().getName());
            return DateUtil.format(d);
        });
        System.out.println("junit method thread:" + Thread.currentThread().getName());
        Assert.assertFalse(future.isDone());
        Assert.assertNull(future.getNow(null));

        // wait for result
        System.out.println("thenApplyAsync result:" + future.join());
        Assert.assertTrue(future.isDone());
    }



    /* 使用线程池完成异步 */
    @Test
    public void TestThenApplyAsyncWithExecutor() {
        String threadpoolMame = "TestThenApplyAsyncWithExecutorThreadPool" ;
        final boolean daemon = true ;
        ExecutorService pool = ThreadPoolUtil.fixedThreadPool(3,threadpoolMame+"-%d",daemon);
        CompletableFuture<String> future = CompletableFuture.completedFuture(new Date()).thenApplyAsync(d->{
            Assert.assertEquals(daemon,Thread.currentThread().isDaemon());
            Assert.assertTrue(Thread.currentThread().getName().startsWith(threadpoolMame));
            System.out.println("thenApplyAsync thread:" + Thread.currentThread().getName());
            return DateUtil.format(d);
        },pool);
        assertNull(future.getNow(null));

        // wait for result
        System.out.println("thenApplyAsync result:" + future.join());
        Assert.assertTrue(future.isDone());
    }

    // 同步消费
    @Test
    public void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message").thenAccept(result::append);
        assertTrue("Result was empty", result.length() > 0);
    }

    // 异步消费
    @Test
    public void thenAcceptAsyncExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture("thenAcceptAsync message").thenAcceptAsync(result::append);
        cf.join();
        assertTrue("Result was empty", result.length() > 0);
    }


    //  异步计算
    @Test
    public void testComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.completedFuture(new Date()).thenApplyAsync(d->{
            CcUt.sleepS(1);
            return DateUtil.format(d);
        });
        Assert.assertFalse(future.isDone());

        // 如果计算未完成，提前结束并设结果为null
        future.complete(null);
        Assert.assertTrue(future.isDone());
        Assert.assertNull(future.get());

        future.join();
        Assert.assertTrue(future.isDone());
        Assert.assertNull(future.get());
    }

    // 计算过程中的异常示例
    @Test
    public void completeExceptionallyExample() {
        CompletableFuture<String> future = CompletableFuture.completedFuture(new Date()).thenApplyAsync(d->{
            CcUt.sleepS(1);
            return DateUtil.format(d);
        });
        CompletableFuture<String> exceptionHandler = future.handle((s, th) -> (th != null) ? "message upon cancel" : "");
        future.completeExceptionally(new RuntimeException("completed exceptionally"));
        Assert.assertTrue("Was not completed exceptionally", future.isCompletedExceptionally());
        try {
            //System.out.println("completeExceptionallyExample result" + future.get());
            System.out.println("completeExceptionallyExample result" + future.join());
            fail("Should have thrown an exception");
        } catch(CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }
        assertEquals("message upon cancel", exceptionHandler.join());
    }


    /*取消计算任务
    与前面一个异常处理的示例类似，我们可以通过调用 cancel(boolean mayInterruptIfRunning)方法取消计算任务。此外，cancel()方法与 completeExceptionally(new CancellationException())等价。
    清单 13. 示例代码*/
    @Test public void cancelExample() {
        CompletableFuture<String> future = CompletableFuture.completedFuture(new Date()).thenApplyAsync(d->{
            CcUt.sleepS(1);
            return DateUtil.format(d);
        });
        CompletableFuture cf2 = future.exceptionally(throwable -> "canceled message");
        assertTrue("Was not canceled", future.cancel(true));
        assertTrue("Was not completed exceptionally", future.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
    }

    private String delayedUpperCase(String s){
        CcUt.sleepS(1);
        return s.toUpperCase();
    }
    private String delayedLowerCase(String s){
        CcUt.sleepS(1);
        return s.toLowerCase();
    }
    private static boolean isUpperCase(String s){
        for(char ch : s.toCharArray()){
            if(ch < 'z' && ch > 'a'){
                return false ;
            }
        }
        return true ;
    }
    /*一个 CompletableFuture VS 两个异步计算
    我们可以创建一个 CompletableFuture 接收两个异步计算的结果，下面代码首先创建了一个 String 对象，接下来分别创建了两个 CompletableFuture 对象 cf1 和 cf2，cf2 通过调用 applyToEither 方法实现我们的需求。
    清单 14. 示例代码*/
    @Test
    public void applyToEitherExample() {
        String original = "Message";
        CompletableFuture<String > future = CompletableFuture.completedFuture(original).thenApplyAsync(this::delayedUpperCase)
            .applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(this::delayedLowerCase),
                s -> s + " from applyToEither");
        assertTrue(future.join().endsWith(" from applyToEither"));
    }
    
    // 使用消费者替换清单 14 的方法方式用于处理异步计算结果
    @Test public void acceptEitherExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(this::delayedUpperCase)
                .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(this::delayedLowerCase),
                        s -> result.append(s).append("acceptEither"));
        cf.join();
        assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
    }
    /*运行两个阶段后执行
    下面这个示例程序两个阶段执行完毕后返回结果，首先将字符转为大写，然后将字符转为小写，在两个计算阶段都结束之后触发 CompletableFuture。
    清单 16. 示例代码*/
    @Test public void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        assertTrue("Result was empty", result.length() > 0);
    }
    // 也可以通过以下方式处理异步计算结果，
    @Test public void thenAcceptBothExample() {
        String original = "Message";
        final StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                (s1, s2) -> result.append(s1).append(s2));
        assertEquals("MESSAGEmessage", result.toString());
    }
    /*整合两个计算结果
    我们可以通过 thenCombine()方法整合两个异步计算的结果，注意，以下代码的整个程序过程是同步的，getNow()方法最终会输出整合后的结果，也就是说大写字符和小写字符的串联值。*/
    @Test public void thenCombineExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(this::delayedUpperCase)
                .thenCombine(CompletableFuture.completedFuture(original).thenApply(this::delayedLowerCase),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.getNow(null));
    }

    // 上面这个示例是按照同步方式执行两个方法后再合成字符串，以下代码采用异步方式同步执行两个方法，由于异步方式情况下不能够确定哪一个方法最终执行完毕，所以我们需要调用 join()方法等待后一个方法结束后再合成字符串，这一点和线程的 join()方法是一致的，主线程生成并起动了子线程，如果子线程里要进行大量的耗时的运算，主线程往往将于子线程之前结束，但是如果主线程处理完其他的事务后，需要用到子线程的处理结果，也就是主线程需要等待子线程执行完成之后再结束，这个时候就要用到 join()方法了，即 join()的作用是："等待该线程终止"。
    @Test public void thenCombineAsyncExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(this::delayedUpperCase)
                .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(this::delayedLowerCase),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.join());
    }
    // 除了 thenCombine()方法以外，还有另外一种方法-thenCompose()，这个方法也会实现两个方法执行后的返回结果的连接。
    @Test public void thenComposeExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(this::delayedUpperCase)
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(this::delayedLowerCase)
                        .thenApply(s -> upper + s));
        assertEquals("MESSAGEmessage", cf.join());
    }
    //anyOf()方法
    // 以下代码模拟了如何在几个计算过程中任意一个完成后创建 CompletableFuture，在这个例子中，我们创建了几个计算过程，然后转换字符串到大写字符。由于这些 CompletableFuture 是同步执行的（下面这个例子使用的是 thenApply()方法，而不是 thenApplyAsync()方法），使用 anyOf()方法后返回的任何一个值都会立即触发 CompletableFuture。然后我们使用 whenComplete(BiConsumer<? super Object, ? super Throwable> action)方法处理结果。
    @Test public void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(this::delayedUpperCase))
                .collect(Collectors.toList());
        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if(th == null) {
                assertTrue(isUpperCase((String) res));
                result.append(res);
            }
        });
        assertTrue("Result was empty", result.length() > 0);
    }
    // 当所有的 CompletableFuture 完成后创建 CompletableFuture
   // 以同步方式执行多个异步计算过程，在所有计算过程都完成后，创建一个 CompletableFuture。

    @Test public void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(this::delayedUpperCase))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> assertTrue(isUpperCase((String)cf.getNow(null))));
            result.append("done");
        });
        assertTrue("Result was empty", result.length() > 0);
    }
    // 相较于前一个同步示例，我们也可以异步执行，如清单 23 所示。
    @Test public void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(this::delayedUpperCase))
                .collect(Collectors.toList());
        CompletableFuture allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((v, th) -> {
                    futures.forEach(cf -> assertTrue(isUpperCase((String)cf.getNow(null))));
                    result.append("done");
                });
        allOf.join();
        assertTrue("Result was empty", result.length() > 0);
    }

    /*
    实际案例
    首先异步地通过调用 cars()方法获取 Car 对象，返回一个 CompletionStage<List>实例。Cars()方法可以在内部使用调用远端服务器上的 REST 服务等类似场景。
    然后和其他的 CompletionStage<List>组合，通过调用 rating(manufacturerId)方法异步地返回 CompletionStage 实例。
    当所有的 Car 对象都被填充了 rating 后，调用 allOf()方法获取最终值。
    调用 whenComplete()方法打印最终的评分（rating）。

    cars().thenCompose(cars -> {
        List<CompletionStage> updatedCars = cars.stream()
                .map(car -> rating(car.manufacturerId).thenApply(r -> {
                    car.setRating(r);
                    return car;
                })).collect(Collectors.toList());
        CompletableFuture done = CompletableFuture
                .allOf(updatedCars.toArray(new CompletableFuture[updatedCars.size()]));
        return done.thenApply(v -> updatedCars.stream().map(CompletionStage::toCompletableFuture)
                .map(CompletableFuture::join).collect(Collectors.toList()));
    }).whenComplete((cars, th) -> {
        if (th == null) {
            cars.forEach(System.out::println);
        } else {
            throw new RuntimeException(th);
        }
    }).toCompletableFuture().join();

    */
}
