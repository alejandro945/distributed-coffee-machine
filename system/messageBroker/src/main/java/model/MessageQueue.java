package model;

import com.google.common.collect.EvictingQueue;
import java.util.Queue;

public class MessageQueue<T> {
    private final Queue<T> queue;

    public MessageQueue(int maxSize) {
        this.queue = EvictingQueue.create(maxSize);
    }

    public void enqueue(T message) {
        queue.add(message);
    }

    public T dequeue() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }
}