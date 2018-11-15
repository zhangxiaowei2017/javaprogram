package com.clazz;

import com.bean.Student;

public class Clazz<E> {
    /**
     * 编译将报错，因为泛型是作用在类上的，是和实例对象绑定到一块的，而静态方法是整个类的，和对象没有关系，
     * 假如静态方法可以使用类的泛型类型，如果多个对象Clazz对象分别使用了不同的泛型类型，此时我们通过类名调用
     * 静态方法时，就会造成静态方法中参数类型的不确定，到底应该使用哪个泛型类型，所以假设不成立，因此类中的静态
     * 方法和静态变量不可以使用类的泛型类型（此处指的泛型类型是:E），如果要为静态方法使用泛型，此时可以定义泛型方法
     * 如当前方法所示。
     * @param e
     */
    public static <E> void getStudent(E e) {

    }

    public static <T,R> int getStudent(Class<T> c, R r) {
        T t = null;
        try {
            t = c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return r.hashCode();
    }
    public static void main(String[] args) {
        int hashCode = getStudent(Student.class,"12") ;
        System.out.println("student : " + hashCode);
    }

}
