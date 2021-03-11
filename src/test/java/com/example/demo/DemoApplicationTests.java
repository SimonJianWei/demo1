package com.example.demo;

import com.example.service.VehicleinfoService;
import com.example.util.ClientCurator;
import com.example.util.QRcodeUtil;
import com.example.util.ZKOperateAPI;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    public VehicleinfoService vehicleinfoService;

    @Autowired
    private ZKOperateAPI zkutil;
    @Autowired
    private QRcodeUtil recodeUtil;
    @Autowired
    private ClientCurator  curator;

    @Test
    void contextLoads() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<VehicleinfoService> testReflect = VehicleinfoService.class;
        Method praticeLambda = testReflect.getMethod("praticeLambda", String.class);
        praticeLambda.invoke(vehicleinfoService, "进入方法");
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20), new ThreadPoolExecutor.DiscardPolicy());
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        Executor pool = new ThreadPoolExecutor(3, 3, 2000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(5));
        Executors.newCachedThreadPool();
//        threadPoolExecutor.execute();
        while (!threadPoolExecutor.isShutdown()){
            if (threadPoolExecutor.getActiveCount() <= 50) {
//                    添加任务
            }else{
//                暂停向其中加入任务
            }
        }

    }

    @Test
    void test1() throws InterruptedException {
//        vehicleinfoService.praticeLambda();
        Interner<String> pool = Interners.newWeakInterner();

        String key = "simon";
        String key2 = "simon";
        com.example.service.Test test = new com.example.service.Test();
        Thread task1 = new Thread() {
            @Override
            public void run() {
                test.test1(pool, key);

            }
        };
        new  Runnable(){
            @Override
            public void run() {

            }
        }  ;
        Thread task2 = new Thread() {
            @Override
            public void run() {
                test.test1(pool, key2);
            }
        };
        Runnable task = () -> {System.out.println("Hello World!");};
        task1.start();
        task2.start();
        Thread.sleep(100000000);
        System.out.println("game over ");
    }


    @Test
    public void test2() {
        List<String> list1 = new ArrayList() {{
            add("1");
            add("2");
        }};
        list1.forEach(System.out::println);
//		List<String> list2= new ArrayList(){{add("3");add("4");}};
    }

    @Test
    void test3() {
        vehicleinfoService.research();
    }

    @Test
    void test4() throws Exception {
        //端口号不写，默认是2181
        zkutil.connect("192.168.30.18");
        zkutil.join("zkPro", "nice", "serious");
        zkutil.list("zkPro");
        zkutil.close();
    }

    @Test
    void test5() {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        list.get(9);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Collections.synchronizedMap(new HashMap<Object, Object>());
    }

    @Test
    void test6() {
        String path = "G:demo.png";
        String logoPath = "C:\\Users\\chenjianwei\\Desktop\\XSH\\切图\\login_bg_04.png";
        String content = "http://fresh-scm.cumminsgps.cn";
        recodeUtil.creatQRCode(content, 600, 600, path, logoPath);
    }


    public static void test7() {
        String s = "jeecg";
        char[] ss = s.toCharArray();
        Boolean flag = true;
        shiro:
        for (int i = 0; i < ss.length; i++) {
            for (int j = i + 1; j < ss.length; j++) {
                if (ss[i] == ss[j]) {
                    flag = false;
                    break shiro;
                }
            }

        }
        System.out.println(flag);
    }

    public static void test8() {
        String s = "jeecg";
        char[] ss = s.toCharArray();
        boolean flag = true;
        for (int i = 0; i < ss.length; i++) {
            if (s.indexOf(ss[i], i + 1) > -1) {
                flag = false;
                break;
            }
        }
        System.out.println(flag);
    }

    public static boolean isValid(String s) {
        HashMap<String, String> hash = new HashMap<String, String>();
        hash.put(")", "(");
        hash.put("]", "[");
        hash.put("}", "{");
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < s.length(); i++) {
            char l = s.charAt(i);
            String str = String.valueOf(l);
            if (!hash.containsKey(str)) {
                stack.push(str);
            } else if (!hash.get(str).equals(stack.pop())) {
                return false;
            }
        }
        return stack.empty();
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        //边界条件判断
        if (nums == null || nums.length == 0)
            return new int[0];
        int res[] = new int[nums.length - k + 1];
        for (int i = 0; i < res.length; i++) {
            int max = nums[i];
            //在每个窗口内找到最大值
            for (int j = 1; j < k; j++) {
                max = Math.max(max, nums[i + j]);
            }
            res[i] = max;
        }
        return res;
    }
     //栈实现队列功能
    class MyQueue {

        private volatile Stack<Integer> in;
        private volatile Stack<Integer> out;
        private  int  front;
        /** Initialize your data structure here. */
        public MyQueue() {
            this.in=new Stack<Integer>();  
            this.out=new Stack<Integer>();
        }

        /** Push element x to the back of queue. */
        public synchronized void push(int x) {
            in.push(x);
            front=x;
        }

        /** Removes the element from in front of queue and returns that element. */
        public synchronized int pop() {
            if(out.empty()){
                //防止新入栈数据影响原先在输出栈的数据
                while(!in.empty()){
                    out.push(in.pop());
                }
            }
            return  out.pop();
        }

        /** Get the front element. */
        public int peek() {
            return  out.empty()?front:out.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return out.empty()&&in.empty();
        }
    }


    @Test
    public  void  test10(){
        curator.init();
        curator.createRecursionPersist("/zkTest/test1","Hello");

    }


    public static void main(String[] args) {
//        Deque<Integer> mDeque = new ArrayDeque<Integer>(16);
//        maxSlidingWindow(new int [] {1,3,-1,-3,5,3,6,7},2);
//        try {
//            Class test = Class.forName("com.example.service.Edward");
//            Class test2 = ClassLoader.getSystemClassLoader().loadClass("com.example.service.Edward");
//            Constructor constructor = test.getConstructor();
//            Edward edward = (Edward) constructor.newInstance();
//             System.out.println(edward instanceof Edward);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        DemoApplicationTests test = new DemoApplicationTests();
        MyQueue myQueue = test.new MyQueue();
        myQueue.push(2);
        System.out.println(myQueue.empty());
    }
}


