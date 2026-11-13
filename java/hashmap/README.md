# 手写 HashMap

基于 Java 17+，使用数组和单向链表实现哈希表。支持泛型、null 键值、put、get、containsKey、remove、size、isEmpty 和 clear。

## 核心流程

- 将 hashCode 与其高 16 位异或，让高位参与桶下标计算。数组容量始终是 2 的幂，用 `hash & (capacity - 1)` 定位桶。
- 哈希冲突时遍历链表，通过哈希和 equals 判断键是否相同。相同则替换值，否则新增节点。
- 元素数大于容量的 0.75 倍时，将数组扩大一倍并重新分配节点。
- put、get、remove 返回 null 时，可能是键不存在，也可能是原值为 null，可用 containsKey 区分。

## 运行

```sh
javac -encoding UTF-8 MyHashMap.java MyHashMapTest.java
java MyHashMapTest
```

验证程序以固定随机种子执行 20000 次操作，并与 JDK HashMap 比较，覆盖冲突、扩容、空键和空值。

这是学习用的精简实现，使用链表处理全部冲突，没有红黑树、迭代器、并发安全及完整的 Map 接口。键放入后应保持 hashCode 和 equals 相关字段稳定。
