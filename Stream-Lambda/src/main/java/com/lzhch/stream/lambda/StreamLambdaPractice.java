package com.lzhch.stream.lambda;

import com.lzhch.stream.lambda.init.InitList;
import com.lzhch.stream.lambda.init.Person;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * stream lambda
 *
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-12-01 16:31
 */
public class StreamLambdaPractice {

    /**
     * 原始集合list, 收集(collect)之后的集合resList
     * <p>
     * !!! list 收集的结果 resList, cloudFactoryList 集合是新的内存地址,但是里面的元素还是 list 中的元素地址;
     * 所以如果修改 resList 中元素的值, list 中的也会改变;
     * 如果要使用原始集合 list 和 收集结果 resList, 则需要对原始集合进行拷贝再操作(注意new ArrayList<>(list)是浅拷贝)
     */


    /**
     * foreach/ stream.foreach
     * foreach 循环的入参是一个 Consumer 类型, 是函数式接口
     * foreach 和 stream.foreach 的效果是一样的
     * foreach 循环更加适合用于简单逻辑的循环遍历(或修改), 不适合进行过多的复杂业务的处理
     */
    public void foreach() {
        List<Person> list = InitList.initList();
        System.out.println("========foreach=======");
        list.forEach(x -> System.out.println(x.toString()));

        System.out.println("========stream.foreach=======");
        list.forEach(x -> System.out.println(x.toString()));

        System.out.println("========foreach complex=======");
        list.forEach(x -> {
            if ("xxx".equals(x.getName())) {
                System.out.println(x);
            }
        });

        list.forEach(x -> x.setAge(10));
        System.out.println(list);
    }

    /**
     * stream.filter
     * filter 是将符合条件的元素过滤出来形成新的流 所以需要新的集合去接收返回值
     */
    public void filter() {
        List<Person> list = InitList.initList();
        System.out.println("========filter=======");
        List<Person> resList = list.stream().filter(x -> x.getAge() > 24).collect(Collectors.toList());
        System.out.println("old list :{} " + list);
        System.out.println("resList :{} " + resList);

        System.out.println("========filter foreach======");
        list.stream().filter(x -> x.getAge() > 24).forEach(System.out::println);

        System.out.println("======== filter findFirst ========");
        Optional<Person> first = list.stream().filter(x -> x.getAge() > 30).findFirst();
        System.out.println("filter findFirst :{} " + first);

        System.out.println("======== filter findAny =======");
        Optional<Person> any = list.stream().filter(x -> x.getAge() > 30).findAny();
        System.out.println("findAny :{} " + any);
    }

    /**
     * find
     * findFirst 匹配第一个元素
     * findAny 匹配任意一个元素, 适合并行流, 也可以跟 filter 等搭配使用
     */
    public void find() {
        List<Person> list = InitList.initList();
        System.out.println("========find first=======");
        Optional<Person> first = list.stream().findFirst();
        Person person = list.stream().findFirst().orElse(null);
        System.out.println("first :{} " + first.get());

        System.out.println("========find any=======");
        Optional<Person> any = list.parallelStream().findAny();
        System.out.println("any :{} " + any.get());
        Optional<Person> anyFilter = list.parallelStream().filter(x -> x.getAge() > 20).findAny();
        System.out.println("any :{} " + anyFilter.get());
    }

    /**
     * match
     * 根据条件进行匹配 返回 boolean 类型
     * allMatch: list 的元素全部满足条件返回 true; 反之 返回 false
     * anyMatch: list 的元素任意一个满足条件返回 true; 反之 返回 false
     * noneMatch: list 的元素全部不满足条件返回 true; 反之 返回 false
     */
    public void match() {
        List<Person> list = InitList.initList();
        System.out.println("========all match=======");
        boolean allMatch = list.stream().allMatch(x -> x.getAge() > 40);
        System.out.println("allMatch :{} " + allMatch);

        System.out.println("========any match=======");
        boolean anyMatch = list.stream().anyMatch(x -> x.getAge() > 70);
        System.out.println("anyMatch :{} " + anyMatch);

        System.out.println("========none match=======");
        boolean noneMatch = list.stream().noneMatch(x -> x.getAge() > 50);
        System.out.println("noneMatch :{} " + noneMatch);
    }

    /**
     * 聚合（max/min/count)
     */
    public void aggregate() {
        List<Person> list = InitList.initList();
        System.out.println("======== max age ========");
        Optional<Person> max = list.stream().max(Comparator.comparingInt(Person::getAge));
        System.out.println("max :{} " + max.get());

        System.out.println("======== min age ========");
        Optional<Person> min = list.stream().min(Comparator.comparing(Person::getAge));
        System.out.println("min :{} " + min.get());

        System.out.println("======== count ========");
        long count = list.stream().filter(x -> x.getAge() > 20).count();
        System.out.println("count :{age > 20} " + count);
    }

    /**
     * 映射(map/flatMap)
     * 映射, 可以将一个流的元素按照一定的映射规则映射到另一个流中
     * map: 接收一个有返回值的函数作为参数, 该函数会被应用到每个元素上, 并将其映射成一个新的元素
     * flatMap: 接收一个有返回值的函数作为参数, 将流中的每个值都换成另一个流, 然后把所有流连接成一个流
     */
    public void mapping() {
        List<Person> list = InitList.initList();

        // 会改变原有集合的值
        System.out.println("======== map 会改变原有集合的值 =======");
        System.out.println("original list :{} " + list);
        // 新建一个 list 统一修改新的 list 的 address 字段为 '这是一个地址', 会修改原有的 list 的值 可以用原有的 list 去接收
        List<Person> newList = list.stream()
                .map(x -> {
                    x.setAge(10);
                    return x;
                })
                .collect(Collectors.toList());
        System.out.println("new list :{} " + newList);

        // 不改变原有集合的值
        System.out.println("======== map 不改变原有集合的值 =======");
        list = InitList.initList();
        System.out.println("original list :{} " + list);
        // 新建一个 list 统一修改新的 list 的 address 字段为 '这是一个地址', 不会修改原有的 list 的值 需要用新的 list 去接收
        List<Person> newList1 = list.stream()
                .map(x -> {
                    Person person = new Person();
                    BeanUtils.copyProperties(x, person);
                    person.setAddress("这是一个地址");
                    return person;
                })
                .collect(Collectors.toList());
        System.out.println("new list :{} " + newList1);

        // map 可以返回不同类型的 list(可用于类型转换)
        System.out.println("======== map 返回不同类型的 list(可用于类型转换) =======");
        list = InitList.initList();
        System.out.println("original list :{} " + list);
        // 将 list 中的 name 属性提取成一个单独的 list
        // List<String> newList3 = list.stream()
        //         .map(x -> {
        //             String person = x.getName();
        //             return person;
        //         })
        //         .collect(Collectors.toList());
        List<String> newList3 = list.stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("new list :{} " + newList3);

        System.out.println("======== 分组后拼接成字符串 =============");
        list = InitList.initList();
        System.out.println("original list :{} " + list);
        String collect = list.stream().map(Person::getName).collect(Collectors.joining(","));
        System.out.println("new String :{} " + collect);

        System.out.println("======== flatMap =======");
        List<String> list1 = Arrays.asList("m-k-l-a", "1-3-5-7");
        List<String> listNew = list1.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split("-");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list1);
        System.out.println("处理后的集合：" + listNew);
    }

    /**
     * 归约(reduce)
     * 也称缩减, 顾名思义, 是把一个流缩减成一个值, 能实现对集合求和、求乘积和求最值操作
     */
    public void reduce() {
        List<Person> list = InitList.initList();
        System.out.println("======== reduce + ========");
        Optional<Integer> reduce = list.stream().map(x -> x.getAge()).reduce((x, y) -> x + y);
        Optional<Integer> reduce1 = list.stream().map(Person::getAge).reduce(Integer::sum);
        Integer reduce2 = list.stream().map(Person::getAge).reduce(0, Integer::sum);
        System.out.println("reduce :{} " + reduce.get());
        System.out.println("reduce1 :{} " + reduce1.get());
        System.out.println("reduce2 :{} " + reduce1.get());

        System.out.println("======== reduce * ========");
        Optional<Integer> reduce3 = list.stream().map(Person::getAge).reduce((x, y) -> x * y);
        System.out.println("reduce3 :{} " + reduce3.get());

        System.out.println("======== reduce max ========");
        Optional<Person> reduce4 = list.stream().reduce((x, y) -> x.getAge() > y.getAge() ? x : y);
        System.out.println("reduce4 :{} " + reduce4.get());
    }

    /**
     * 收集-分组 collect-groupBy
     * 将一个 list 进行分组转换成多个 Map, 可以进行单级分组和多级分组
     * 入参是 Function 接口
     */
    public void groupBy() {
        List<Person> list = InitList.initList();
        System.out.println("========== groupBy =============");
        System.out.println("=========== 根据单个属性分组 ============");
        Map<String, List<Person>> collect = list.stream().collect(Collectors.groupingBy(Person::getName));
        System.out.println("groupBy :{name} " + collect.toString());
        List<String> nameLength = list.stream().collect(Collectors.groupingBy(Person::getName)).keySet().stream().sorted(Comparator.comparing(String::length).reversed()).collect(Collectors.toList());
        System.out.println("max :{name.length} " + nameLength);

        System.out.println("=========== 根据多个属性拼接分组 ============");
        Map<String, List<Person>> collect1 = list.stream().collect(Collectors.groupingBy(x -> x.getName() + "-" + x.getAge()));
        System.out.println("groupBy :{name-age} " + collect1.toString());

        System.out.println("=========== 根据多条件分组 ============");
        Map<String, List<Person>> collect2 = list.stream().collect(Collectors.groupingBy(x -> {
                    if (x.getAge() > 30) {
                        return "age>30";
                    } else {
                        return "ageOther";
                    }
                }
        ));
        System.out.println("groupBy :{age>30} " + collect2.toString());

        System.out.println("=========== 按子组收集数据[求总数] ============");
        // 分别求男女性别的人数
        Map<String, Long> collect3 = list.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.counting()));
        System.out.println("groupBy :{counting} " + collect3.toString());

        System.out.println("=========== 按子组收集数据[求和] ============");
        // 分别求男性的年龄和以及女性的年龄和
        Map<String, Integer> collect4 = list.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.summingInt(Person::getAge)));
        System.out.println("groupBy :{summing} " + collect4.toString());

        System.out.println("=========== 按子组收集数据[平均值] ============");
        // 分别求男性的平均年龄和女性的平均年龄
        Map<String, Double> collect5 = list.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.averagingInt(Person::getAge)));
        System.out.println("groupBy :{averaging} " + collect5.toString());

        System.out.println("=========== 按子组收集数据[求和/总数/平均值/极值等] ============");
        // Collectors.summarizingDouble();Collectors.summarizingLong(); 同理,只是数据类型不一样
        // 分别求男女性别的一些数值数据
        Map<String, IntSummaryStatistics> collect6 = list.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.summarizingInt(Person::getAge)));
        System.out.println("groupBy :{summarizing} " + collect6.toString());

        System.out.println("========== 按子组收集数据[根据性别分组,每组只保留年龄最大的] =============");
        // 根据性别分组,每组只保留年龄最大的, 如果有年龄相同的默认保存第一个
        Map<String, Person> collect7 = list.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Person::getAge)), Optional::get)));
        // idea 建议的替代 collect7 的方法
        Map<String, Person> collect7_1 = list.stream().collect(Collectors.toMap(Person::getSex, Function.identity(), BinaryOperator.maxBy(Comparator.comparing(Person::getAge))));
        System.out.println("groupBy :{} " + collect7.toString());

        System.out.println("========== 联合其他收集器[根据性别分组,每组根据姓名分组,只保留姓名] ===========");
        Map<String, Set<String>> collect8 = list.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.mapping(Person::getName, Collectors.toSet())));
        System.out.println("groupBy :{} " + collect8.toString());
    }

    /**
     * 收集-分区(collect-partitioningBy)
     * 将流按照一定条件转化为两个 Map (入参是 Predicate 接口, 只能根据 true 和 false 分为两组)
     */
    public void partitioningBy() {
        List<Person> list = InitList.initList();
        System.out.println("========== partitioningBy =============");
        Map<Boolean, List<Person>> collect = list.stream().collect(Collectors.partitioningBy(x -> x.getAge() > 40));
        System.out.println("partitioningBy :{} " + collect.toString());
    }

    /**
     * 收集-归集（toList/toSet/toMap)
     * 因为流不存储数据，那么在流中的数据处理完成后，需要将流中的数据重新归集到新的集合里
     */
    public void otherCollections() {
        List<Person> list = InitList.initList();
        System.out.println("========== 转换为其他的集合形式 =============");
        System.out.println("========== toList(年龄大于 40 的人) =============");
        List<Person> collect = list.stream().filter(x -> x.getAge() > 40).collect(Collectors.toList());
        System.out.println("toList(年龄大于 40 的人) :{} " + collect);

        System.out.println("========== toSet(取出所有人的性别) =============");
        Set<String> collect1 = list.stream().map(Person::getSex).collect(Collectors.toSet());
        System.out.println("toSet(取出所有人的性别) :{} " + collect1);

        System.out.println("========== toMap(年龄大于 30 的按照姓名分组) =============");
        Map<String, Person> collect2 = list.stream().filter(x -> x.getAge() > 30).collect(Collectors.toMap(Person::getName, x -> x));
        // (k1, k2) -> k1: key 重复时使用旧对象, 防止报错(默认 key 重复报错)
        Map<String, Person> collect3 = list.stream().filter(x -> x.getAge() > 30).collect(Collectors.toMap(Person::getName, x -> x, (k1, k2) -> k1));
        // 使用 toMap() 实现 list 转 map, 默认返回 HashMap, HashMap 是无序的, LinkedHashMap 可以实现有序(groupingBy同理)
        LinkedHashMap<String, Person> collect4 = list.stream().filter(x -> x.getAge() > 30).collect(Collectors.toMap(Person::getName, x -> x, (k1, k2) -> k1, LinkedHashMap::new));
        System.out.println("toMap(年龄大于 30 的按照姓名分组) :{} " + collect2);
    }

    /**
     * 收集-统计(count/averaging/maxBy/summing/summarizing)
     * Collectors提供了一系列用于数据统计的静态方法：
     * 计数：count
     * 平均值：averagingInt、averagingLong、averagingDouble
     * 最值：maxBy、minBy
     * 求和：summingInt、summingLong、summingDouble
     * 统计以上所有：summarizingInt、summarizingLong、summarizingDouble
     */
    public void statistical() {
        List<Person> list = InitList.initList();
        System.out.println("========== Collectors 提供的用于数据统计的静态方法 =============");
        System.out.println("========== 求总数[年龄大于 40 的人数] ==========");
        Long collect = list.stream().filter(x -> x.getAge() > 40).collect(Collectors.counting());
        // 与 collect 等价
        Long collect_ = list.stream().filter(x -> x.getAge() > 40).count();
        System.out.println("求总数[年龄大于 40 的人数] :{} " + collect.toString());

        System.out.println("========== 求平均数[年龄大于 30 的人的平均工资] ==========");
        // averagingInt、averagingLong 同理
        Double collect1 = list.stream().filter(x -> x.getAge() > 30).collect(Collectors.averagingDouble(Person::getSalary));
        System.out.println("求平均数[年龄大于 30 的人的平均工资] :{} " + collect1);

        System.out.println("========== 求最值[求最高工资] ==========");
        // minBy 同理
        Optional<Integer> collect2 = list.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        Optional<Integer> collect2_1 = list.stream().map(Person::getSalary).max(Integer::compare);
        System.out.println("求最值[求最高工资] :{} " + collect2.get());

        System.out.println("========== 求和[求年龄和] ==========");
        // summingLong、summingDouble 同理
        Integer collect3 = list.stream().collect(Collectors.summingInt(Person::getAge));
        Integer collect3_1 = list.stream().mapToInt(Person::getAge).sum();
        System.out.println("求和[求年龄和] :{} " + collect3);

        System.out.println("========== 求以上所有信息[总数/平均/最值/和] ==========");
        DoubleSummaryStatistics collect4 = list.stream().collect(Collectors.summarizingDouble(Person::getSalary));
        System.out.println("求工资的总数/平均/最值/和] :{} " + collect4);
    }

    /**
     * 集合-接合(joining)
     * joining 可以将 Stream 中的元素用特定的连接符连接成一个字符串，没有指定连接符则直接连接。
     */
    public void joining() {
        List<Person> list = InitList.initList();
        System.out.println("======== joining ===========");
        System.out.println("======== 将所有的人的姓名按照,组成一个新的字符串");
        String collect = list.stream().map(Person::getName).collect(Collectors.joining(","));
        System.out.println("result :{} " + collect);
    }

    /**
     * 集合-规约(collect-reduce)
     * Collectors 类提供的 reducing 方法，相比于 stream 本身的 reduce 方法，增加了对自定义规约的支持。
     */
    public void collectReduce() {
        List<Person> list = InitList.initList();
        System.out.println("======== collect-reduce[计算虚岁年龄和] ===========");
        // 从 0 开始计算虚岁年龄和, 循环中每个年龄元素+1
        Integer collect = list.stream().collect(Collectors.reducing(0, Person::getAge, (x, y) -> x + y + 1));
        Integer collect_ = list.stream().map(Person::getAge).reduce(0, (x, y) -> x + y + 1);
        System.out.println("result :{} " + collect.toString());
    }

    /**
     * sorted 排序
     * sorted 是中间操作，有两种排序：
     * 1. sorted()：自然排序，流中元素需实现Comparable接口
     * 2. sorted(Comparator com)：Comparator排序器自定义排序
     */
    public void sorted() {
        List<Person> list = InitList.initList();
        System.out.println("========== sorted =============");
        System.out.println("=========== 单个条件排序(根据年龄倒序排序) ==============");
        List<Person> list1 = list.stream().sorted(Comparator.comparing(Person::getAge).reversed()).collect(Collectors.toList());
        System.out.println("sorted :{} " + list1);

        System.out.println("=========== 多条件排序(根据年龄降序并根据工资升序) ================");
        List<Person> list2 = list.stream().sorted(Comparator.comparing(Person::getAge).reversed().thenComparing(Person::getSalary)).collect(Collectors.toList());
        System.out.println("sorted :{} " + list2);

        System.out.println("=========== 多条件排序(根据年龄降序并根据工资降序) ================");
        List<Person> list3 = list.stream().sorted(Comparator.comparing(Person::getAge).reversed()).sorted(Comparator.comparing(Person::getSalary).reversed()).collect(Collectors.toList());
        System.out.println("sorted :{} " + list3);

        System.out.println("=========== 多条件排序(根据年龄降序并根据工资降序2) ================");
        List<Person> list4 = list.stream().sorted((x, y) -> {
            if (x.getAge() == y.getAge()) {
                // return > 0 升序; return < 0 降序
                return y.getSalary() - x.getSalary();
            } else {
                return y.getAge() - x.getAge();
            }
        }).collect(Collectors.toList());
        System.out.println("sorted :{} " + list4);
    }

    /**
     * 提取、组合（concat/distinct/limit/skip）
     */
    public void other() {
        List<Person> list = InitList.initList();
        System.out.println("======== concat[合并两个流] =========");
        List<Person> list1 = InitList.initList();
        List<Person> collect = Stream.concat(list.stream(), list1.stream()).collect(Collectors.toList());
        System.out.println("concat :{} " + collect);

        System.out.println("======== distinct[去重] ==========");
        List<Person> collect1 = Stream.concat(list.stream(), list1.stream()).distinct().collect(Collectors.toList());
        System.out.println("distinct :{} " + collect1);

        System.out.println("======== limit[限制从流中获得前n个数据] ==========");
        List<Person> collect2 = list.stream().limit(2).collect(Collectors.toList());
        System.out.println("limit :{} " + collect2);
        System.out.println("======== limit[获取年龄最大的两个人] ==========");
        List<Person> collect3 = list.stream().sorted(Comparator.comparing(Person::getAge).reversed()).limit(2).collect(Collectors.toList());
        System.out.println("result :{} " + collect3);


        System.out.println("======== skip[跳过前n个数据] ==========");
        List<Person> collect4 = list.stream().skip(1).collect(Collectors.toList());
        System.out.println("skip :{} " + collect4);
        System.out.println("======== skip[跳过工资最低的一个人] ==========");
        List<Person> collect5 = list.stream().sorted(Comparator.comparing(Person::getSalary)).skip(1).collect(Collectors.toList());
        System.out.println("result :{} " + collect5);
    }

    /**
     * 根据对象的单个或者多个属性去重
     */
    public void removeDuplicates() {
        List<Person> list = InitList.initList();
        System.out.println("========= 根据单个属性(name)去重 ==========");
        System.out.println("去重前 :{} " + list);
        ArrayList<Person> collect = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(Person::getName))
                        ), ArrayList::new
                )
        );
        System.out.println("去重后 :{} " + collect.toString());

        System.out.println("========= 根据多个属性(name+age)去重 ==========");
        System.out.println("去重前 :{} " + list);
        ArrayList<Person> collect1 = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(item -> item.getName() + item.getAge()))
                        ), ArrayList::new
                )
        );
        System.out.println("去重后 :{} " + collect1.toString());

        /**
         *  distinct 去重
         *  如果是自定义对象则必须重写 equals() 和 hashcode() 方法
         *  lombok 的 @Data 注解默认重写了 equals() 和 hashcode()
         *  但是如果有父类的话 还需要加上 @EqualsAndHashCode(callSuper = true) 和 @Data 一起使用
         *  因为 @Data 只会重写当前类的属性 不会加上父类的属性 会在某些场景下出现问题
         */
        System.out.println("======== distinct 去重 ==========");
        System.out.println("去重前 :{} " + list);
        List<Person> collect2 = list.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后 :{} " + collect2);

        System.out.println("======== distinct 去重(Integer String....) ===========");
        List<Object> objects = new ArrayList<>(10);
        objects.add(1);
        objects.add(1);
        objects.add(1);
        objects.add(2);
        objects.add(2);
        objects.add(3);
        objects.add("1");
        objects.add("1");
        objects.add("2");
        objects.add("3");
        objects.add("3");
        System.out.println("去重前 :{} " + objects);
        List<Object> collect3 = objects.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后 :{} " + collect3);
    }

}
