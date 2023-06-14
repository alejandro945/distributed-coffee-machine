package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

public class ThreadPoolService {
    // @Constants
    private final int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    // @Attributes
    private static ThreadPoolService instance = null;
    private ExecutorService pool;
    private Semaphore sem;

    /**
     * Method responsible for returning the instance
     * 
     * @return instance
     */
    private ThreadPoolService() {
        this.pool = java.util.concurrent.Executors.newFixedThreadPool(MAX_THREADS);
        this.sem = new Semaphore(1);
    }

    /**
     * Method responsible for returning the instance
     * 
     * @return instance
     */
    public static ThreadPoolService getInstance() {
        if (instance == null)
            instance = new ThreadPoolService();
        return instance;
    }

    /**
     * Method responsible for returning the pool
     * 
     * @return pool
     */
    public ExecutorService getPool() {
        return this.pool;
    }

    /**
     * Method responsible for returning the semaphore
     * 
     * @return semaphore
     */
    public Semaphore getSemaphore() {
        return this.sem;
    }

    /**
     * Method responsible for executing a task
     * 
     * @param task
     */
    public void execute(TaskService task) {
        this.pool.execute(task);
    }
}
