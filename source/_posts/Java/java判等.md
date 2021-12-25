---
title: java判等解析
description: '==，equal该怎么用，易错点，Integer String判等案例，为什么重写hashcode'
tags:
  - java
  - 编码
categories:
  - java
  - 编码
abbrlink: 11e564a
date: 2021-12-16 12:44:00
---

在业务代码中，我们通常使用 equals 或 == 进行判等操作。equals 是方法而 == 是操作符，它们的使用是有区别的：

* 对基本类型，比如 int、long，进行判等，只能使用 ==，比较的是直接值。因为基本类型的值就是其数值。

* 对引用类型，比如 Integer、Long 和 String，进行判等，需要使用 equals 进行内容判等。因为引用类型的直接值是指针，使用 == 的话，比较的是指针，也就是两个对象在内存中的地址，即比较它们是不是同一个对象，而不是比较对象的内容。

## 基本类型只能使用 == ，其他类型都需要使用 equals

### Integer判等案例

```java
public class IntegerEqualsTest {

  @Test
  public void test() {

    /**
     * 编译器会把 Integer a = 127 转换为 Integer.valueOf(127)。
     * 查看源码可以发现，这个转换在内部其实做了缓存，使得两个 Integer 指向同一个对象，所以 == 返回 true
     */
    Integer a = 127; //Integer.valueOf(127)
    Integer b = 127; //Integer.valueOf(127)
    assertTrue(a == b);

    /**
     * 之所以同样的代码 128 就返回 false 的原因是，默认情况下会缓存[-128, 127]的数值，而 128 处于这个区间之外
     * 设置 JVM 参数加上 -XX:AutoBoxCacheMax=1000 再试试，是不是就返回 true 了呢
     */
    Integer c = 128; //Integer.valueOf(128)
    Integer d = 128; //Integer.valueOf(128)
    assertFalse(c == d);

    //比较一个新对象和一个来自缓存的对象
    Integer e = 127; //Integer.valueOf(127)
    Integer f = new Integer(127); //new instance
    assertFalse(e == f);

    //比较两个新对象
    Integer g = new Integer(127); //new instance
    Integer h = new Integer(127); //new instance
    assertFalse(g == h);

    //较，前者会先拆箱再比较，比较的肯定是数值而不是引用，因此返回 true
    Integer i = 128; //unbox
    int j = 128;
    assertTrue(i == j);
  }

}
```

### 字符串判等案例

```java
public class StringEqualsTest {

  @Test
  public void test() {
    //因为 Java 的字符串驻留机制，直接使用双引号声明出来的两个 String 对象指向常量池中的相同字符串
    String a = "1";
    String b = "1";
    assertTrue(a == b);

    //new 出来的两个 String 是不同对象，引用当然不同，所以得到 false 的结果
    String c = new String("2");
    String d = new String("2");
    assertFalse(c == d);

    //使用 String 提供的 intern 方法也会走常量池机制，所以同样能得到 true
    String e = new String("3").intern();
    String f = new String("3").intern();
    assertTrue( e == f);

    //通过 equals 对值内容判等，是正确的处理方式，当然会得到 true
    String g = new String("4");
    String h = new String("4");
    assertTrue(g.equals(h));
  }
}
```

## 重写equals

实现一个更好的 equals 应该注意的点：

* 考虑到性能，可以先进行指针判等，如果对象是同一个那么直接返回 true；
* 需要对另一方进行判空，空对象和自身进行比较，结果一定是 fasle；
* 需要判断两个对象的类型，如果类型都不同，那么直接返回 false；
* 确保类型相同的情况下再进行类型强制转换，然后逐一判断所有字段。

### Object equals源码

```java
 public boolean equals(Object obj) {
 	return (this == obj);
 }
```

### String equals源码

```java
public boolean equals(Object anObject) {
    if (this == anObject) {
        return true;
    }
    if (anObject instanceof String) {
        String aString = (String)anObject;
        if (coder() == aString.coder()) {
            return isLatin1() ? StringLatin1.equals(value, aString.value)
                : StringUTF16.equals(value, aString.value);
        }
    }
    return false;
}
```

### Integer equals源码

```java
 public boolean equals(Object obj) {
 	if (obj instanceof Integer) {
 		return value == ((Integer)obj).intValue();
 	}
	return false;
 }
```

## 为什么要重写hashCode 

如果重写了equals，比如说是基于对象的内容实现的，而没有重写hashCode，就会调用Object类中的hashCode，那么就会导致两个逻辑上相等的对象，hashCode却不一样。这样，当你用其中一个对象作为key保存到HashMap、HashSet或者Hashtable中，再以另一个“相等的”对象作为key去取值，则根本找不到。因此，为什么重写equals一定要重写hashCode，其实简单来说就是为了保证两个对象在equals相同的情况下hashCode值也必定相同。

#### 总结一下要点：

* hashCode主要用于提升查询效率，来确定在散列结构中对象的存储地址；
* 重写equals()必须重写hashCode()，二者参与计算的自身属性字段应该相同；
* hash类型的存储结构，添加元素重复性校验的标准就是先取hashCode值，后判断equals()；
* equals()相等的两个对象，hashcode()一定相等；
* 反过来：hashcode()不等，一定能推出equals()也不等；
* hashcode()相等，equals()可能相等，也可能不等。

#### 怎么提升的效率

java中的List集合是有序可重复的，而set集合是无序不能重复的，那么怎么能保证不能被放入重复的元素呢，但靠equals方法一样比较的话，如果原来集合中以后又10000个元素了，那么放入10001个元素，难道要将前面的所有元素都进行比较，看看是否有重复，欧码噶的，这个效率可想而知，因此hashcode就应遇而生了，java就采用了hash表，利用哈希算法（也叫散列算法），就是将对象数据根据该对象的特征使用特定的算法将其定义到一个地址上，那么在后面定义进来的数据只要看对应的hashcode地址上是否有值，那么就用equals比较，如果没有则直接插入，只要就大大减少了equals的使用次数，执行效率就大大提高了。

#### 案例

```java
public class PersonEqualsTest {

  /**
   * 使用Object的equals
   */
  @Test
  public void test() {
    Person lisi1 = new Person("lisi", 12);
    Person lisi2 = new Person("lisi", 12);

    Assertions.assertFalse(lisi1.equals(lisi2));
    System.out.println(lisi1.hashCode());
    System.out.println(lisi2.hashCode());
    Assertions.assertFalse(lisi1.hashCode() == lisi2.hashCode());
  }

  /**
   * 只重写eauals，只有name参与
   */
  @Test
  public void testRewriteEquals() {
    PersonRewriteEquals lisi1 = new PersonRewriteEquals("lisi", 13);
    PersonRewriteEquals lisi2 = new PersonRewriteEquals("lisi", 12);

    Assertions.assertTrue(lisi1.equals(lisi2));
    System.out.println(lisi1.hashCode());
    System.out.println(lisi2.hashCode());
    Assertions.assertFalse(lisi1.hashCode() == lisi2.hashCode());
  }

  /**
   * 只重写hash，只有name参与
   */
  @Test
  public void testRewriteHash () {
    PersonRewriteHash lisi1 = new PersonRewriteHash("lisi", 13);
    PersonRewriteHash lisi2 = new PersonRewriteHash("lisi", 12);

    Assertions.assertFalse(lisi1.equals(lisi2));
    System.out.println(lisi1.hashCode());
    System.out.println(lisi2.hashCode());
    Assertions.assertTrue(lisi1.hashCode() == lisi2.hashCode());
  }

  /**
   * 重写equals和hash
   */
  @Test
  public void testRewriteEqualsAndHash () {
    PersonRewriteEqualsAndHash lisi1 = new PersonRewriteEqualsAndHash("lisi", 12);
    PersonRewriteEqualsAndHash lisi2 = new PersonRewriteEqualsAndHash("lisi", 12);

    Assertions.assertTrue(lisi1.equals(lisi2));
    System.out.println(lisi1.hashCode());
    System.out.println(lisi2.hashCode());
    Assertions.assertTrue(lisi1.hashCode() == lisi2.hashCode());
  }
  @Test
  public void testRewriteEqualsAndHash2 () {
    PersonRewriteEqualsAndHash lisi1 = new PersonRewriteEqualsAndHash("lisi", 12);
    PersonRewriteEqualsAndHash lisi2 = new PersonRewriteEqualsAndHash("lisi", 12);

    HashMap<PersonRewriteEqualsAndHash, String> map = new HashMap<>();
    map.put(lisi1, "lisi1");
    map.put(lisi2, "lisi2");
	// 重写hash和equals后
    Assertions.assertEquals("lisi2",map.get(lisi1));
    Assertions.assertEquals("lisi2",map.get(lisi2));
  }
}

```





[本位源码仓库]: https://github.com/qypone/learn

