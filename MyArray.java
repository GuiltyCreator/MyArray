package com.a7279.MyArray;

import java.util.Objects;

/**
 * 我的数组类
 *
 * creator:硝酸铜
 * 2019/8/31
 */
public class MyArray<T> {
    private T[] data;
    private int capacity;//容量
    private int size;//表示数组中有多少个元素,指向第一个没有元素的位置

    /**
     * 构造函数，传入容量capacity
     * @param capacity
     */
    public MyArray(int capacity){
        this.capacity=capacity;
        this.size=0;
        data=(T[]) new Object[capacity];
    }

    /**
     * 无参构造函数
     */
    public MyArray(){
        this(10);
    }

    /**
     * 构造函数，根据传入数组进行初始化
     * @param data
     */
    public MyArray(T[] data){
        this.data=data;
        this.capacity=data.length;
        this.size=data.length;
    }

    /**
     * 获取元素个数
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * 获取数组容量
     * @return
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * 判断数组是否为空
     * @return
     */
    public boolean isEmpty(){
        return size==0;
    }

    /**
     * 在最后一个位置添加元素
     * @param item
     */
    public void addLast(T item) {
        add(size,item);
    }

    /**
     * 在第一个位置添加元素
     * @param item
     */
    public void addFirst(T item){
        add(0,item);
    }

    /**
     * 在指定地方插入元素
     *
     * @param index
     * @param item
     */
    public void add(int index, T item) {

        //index要大于0并且小于size，因为元素要紧密相连
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("AddLast failed.Require index >= 0 and index < size.");
        }

        if (this.size == this.capacity) {
            resize(2 * capacity);
        }

        for (int i = size - 1; i >= index; i--) {
            this.data[i + 1] = this.data[i];
        }
        this.data[index] = item;
        size++;
    }

    /**
     * 对数组进行扩容
     * @param newCapacity
     */
    private void resize(int newCapacity){
        T[] newData=(T[]) new Object[newCapacity];
        for (int i=0;i<size;i++){
            newData[i]=data[i];
        }
        data = newData;

    }
    /**
     * 获取元素
     *
     * @param index
     * @return
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            //用户无法获取数组没有使用的空间
            throw new IllegalArgumentException("Get failed.Index is illegal.");
        }
        return this.data[index];
    }

    /**
     * 修改元素
     *
     * @param index
     * @param item
     */
    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            //用户无法获取数组没有使用的空间
            throw new IllegalArgumentException("Set failed.Index is illegal.");
        }
        this.data[index] = item;
    }

    /**
     * 是否包含某元素
     * @param item
     * @return
     */
    public boolean contains(T item) {
        for (T i : this.data) {
            if (Objects.equals(item, i)) {
                return true;

            }
        }
        return false;
    }

    /**
     * 查找元素所在索引，若没有找到则返回-1
     * @param item
     * @return
     */
    public int find(T item) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(item, this.data[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 删除指定位置的元素
     * @param index
     * @return 返回删除的元素
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            //index合法性判断
            throw new IllegalArgumentException("Remove failed.Index is illegal.");
        }
        T res = this.data[index];
        for (int i = index + 1; i < size; i++) {
            this.data[i - 1] = data[i];
        }
        size--;
        data[size] = null;//将引用置空，好让垃圾回收机制发生
        if(size == capacity/4 && capacity/2 !=0){//使用Lazy策略，防止复杂度震荡
            resize(capacity/2);
        }
        return res;
    }

    /**
     * 删除第一个元素
     * @return
     */
    public T removeFirst() {
        return remove(0);
    }

    /**
     * 删除最后一个元素
     * @return
     */
    public T removeLast() {
        return remove(size - 1);
    }

    /**
     * 删除已有的元素，包括重复的元素
     * @param item
     * @return 进行了删除操作返回true，没有则返回false
     */
    public boolean removeElement(T item) {
        boolean falg=true;
        boolean res=false;
        int index;
        while (falg){
            index=find(item);
            if (index == -1) {
                falg=false;
            }else {
                remove(index);
                res=true;
            }
        }
        return res;
    }


    @Override
    public String toString() {
        StringBuilder res=new StringBuilder();
        //%d是一个占位符
        res.append("MyArray:size="+this.size+",capacity="+this.capacity+"\n");
        res.append("[");
        for(int i=0;i<size;i++){
            res.append(this.data[i].toString());
            if(i!=size-1){
                res.append(",");
            }
        }
        res.append("]");
        return res.toString();
    }
}
