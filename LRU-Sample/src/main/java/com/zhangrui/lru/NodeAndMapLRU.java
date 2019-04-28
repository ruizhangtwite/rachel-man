package com.zhangrui.lru;

import java.util.HashMap;

/**
 * Desp:
 * 2019-04-28 22:36
 * Created by zhangrui.
 */
public class NodeAndMapLRU<K, V> {

    private Node head = null;
    private Node tail = null;

    private HashMap<K, Node> map;

    private Integer size;

    public NodeAndMapLRU(int size) {
        this.size = size;
        this.map = new HashMap<K, Node>(size);
    }

    //头插法
    public void put(K key, V value) {
        Node node = this.map.get(key);
        Node lastNode = null;
        if (node == null){
            if (this.map.size() >= size){
                lastNode = removeLast();
                this.map.remove(lastNode.key);
            }
            
            node = new Node(key, value);
        }
        
        node.value = value;
        this.map.put(key, node);
        
        
        if (head != null && tail != null){
            moveToHead(node);
        }
        
        if (head == null){
            head = node;
        }
        if (tail == null){
            tail = node;
        }

       
        
    }

    private Node removeLast() {
        Node node = tail;
        if (tail != null){
            tail = tail.prev;
            if (tail != null){
                tail.next = null;
            }
        }
        return node;
    }

    public V get(K key) {
        Node node = this.map.get(key);

        if (node != null) {
            Node headNode = head;
            while (headNode != null) {
                if (headNode.key == key) {
                    moveToHead(node);
                    break;
                }
                headNode = headNode.next;
            }
            
            return (V)node.value;
        }


        return null;
    }

    private void moveToHead(Node node) {
        
        if (node == head){
            return;
        }
        
        if (node == tail){
            
            tail = tail.prev;
            tail.next = null;
            node.next = null;
            node.prev = null;
        }
        
        if (node.next == null && node.prev == null){
            node.next = head;
            head = node;
            head.next.prev = node;
            return;
        }
        
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = head;
        head.prev = node;
        head = node;
        head.prev = null;

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node node = head;
        while(node != null){
            sb.append(String.format("%s:%s ", node.key,node.value));
            node = node.next;
        }

        return sb.toString();
    }
    
    public static void main(String[] args) {
        NodeAndMapLRU<Integer, Integer> lru = new NodeAndMapLRU<Integer, Integer>(3);
        lru.put(10, 20);
        lru.put(20, 30);
        lru.put(10, 30);
        lru.put(40, 20);
        lru.put(20, 15);

        System.out.println(lru.get(10));
        System.out.println(lru.toString());
    }


    class Node<K, V> {
        Node prev;
        Node next;
        K key;
        V value;

        public Node(K key,V value) {
            this.key = key;
            this.value = value;
        }

    }
}
