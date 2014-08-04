package hu.list;

import hu.list.tuple.HUTuple2;
import hu.list.tuple.HUTuple3;
import hu.list.tuple.HUTuple4;
import hu.list.tuple.HUTuple5;
import hu.list.tuple.HUTuple6;
import hu.list.tuple.HUTuple7;

/**
 * 局所性の評価をおこなうためのクラス
 * タプル間のインターバルの平均を求めるメソッドが定義されている. 
 * @author hozumi
 *
 */
public class HUEvaluation
{
    public static int intervalT2(HUSet<HUTuple2<Integer, Integer>> set)
    {
        return set.fold(intervalT2, intervalSeedT2).el0() / set.size();
    }

    public static int intervalT3(HUSet<HUTuple3<Integer, Integer, Integer>> set)
    {
        return set.fold(intervalT3, intervalSeedT3).el0() / set.size();
    }

    public static int intervalT4(
            HUSet<HUTuple4<Integer, Integer, Integer, Integer>> set)
    {
        return set.fold(intervalT4, intervalSeedT4).el0() / set.size();
    }

    public static int intervalT5(
            HUSet<HUTuple5<Integer, Integer, Integer, Integer, Integer>> set)
    {
        return set.fold(intervalT5, intervalSeedT5).el0() / set.size();
    }

    public static int intervalT6(
            HUSet<HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>> set)
    {
        return set.fold(intervalT6, intervalSeedT6).el0() / set.size();
    }

    public static int intervalT7(
            HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> set)
    {
        return set.fold(intervalT7, intervalSeedT7).el0() / set.size();
    }

    private static HUFolder<HUTuple2<Integer, HUTuple2<Integer, Integer>>, HUTuple2<Integer, Integer>>                                                                                           intervalT2     = new HUFolder<HUTuple2<Integer, HUTuple2<Integer, Integer>>, HUTuple2<Integer, Integer>>() {
                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                    public HUTuple2<Integer, HUTuple2<Integer, Integer>> apply(
                                                                                                                                                                                                                            HUTuple2<Integer, HUTuple2<Integer, Integer>> capsule,
                                                                                                                                                                                                                            HUTuple2<Integer, Integer> cur)
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                        HUTuple2<Integer, Integer> pre = capsule.el1();
                                                                                                                                                                                                                        int sum = capsule.el0();
                                                                                                                                                                                                                        if (pre != null)
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                            sum += Math.abs(cur.el1()
                                                                                                                                                                                                                                    - pre.el1());
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        return new HUTuple2<Integer, HUTuple2<Integer, Integer>>(
                                                                                                                                                                                                                                sum,
                                                                                                                                                                                                                                cur);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                };

    private static HUTuple2<Integer, HUTuple2<Integer, Integer>>                                                                                                                                 intervalSeedT2 = new HUTuple2<Integer, HUTuple2<Integer, Integer>>(
                                                                                                                                                                                                                        0,
                                                                                                                                                                                                                        null);

    private static HUFolder<HUTuple2<Integer, HUTuple3<Integer, Integer, Integer>>, HUTuple3<Integer, Integer, Integer>>                                                                         intervalT3     = new HUFolder<HUTuple2<Integer, HUTuple3<Integer, Integer, Integer>>, HUTuple3<Integer, Integer, Integer>>() {
                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                    public HUTuple2<Integer, HUTuple3<Integer, Integer, Integer>> apply(
                                                                                                                                                                                                                            HUTuple2<Integer, HUTuple3<Integer, Integer, Integer>> capsule,
                                                                                                                                                                                                                            HUTuple3<Integer, Integer, Integer> cur)
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                        HUTuple3<Integer, Integer, Integer> pre = capsule.el1();
                                                                                                                                                                                                                        int sum = capsule.el0();
                                                                                                                                                                                                                        if (pre != null)
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                            sum += Math.abs(cur.el1()
                                                                                                                                                                                                                                    - pre.el1())
                                                                                                                                                                                                                                    + Math.abs(cur.el2()
                                                                                                                                                                                                                                            - pre.el2());
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        return new HUTuple2<Integer, HUTuple3<Integer, Integer, Integer>>(
                                                                                                                                                                                                                                sum,
                                                                                                                                                                                                                                cur);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                };

    private static HUTuple2<Integer, HUTuple3<Integer, Integer, Integer>>                                                                                                                        intervalSeedT3 = new HUTuple2<Integer, HUTuple3<Integer, Integer, Integer>>(
                                                                                                                                                                                                                        0,
                                                                                                                                                                                                                        null);

    private static HUFolder<HUTuple2<Integer, HUTuple4<Integer, Integer, Integer, Integer>>, HUTuple4<Integer, Integer, Integer, Integer>>                                                       intervalT4     = new HUFolder<HUTuple2<Integer, HUTuple4<Integer, Integer, Integer, Integer>>, HUTuple4<Integer, Integer, Integer, Integer>>() {
                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                    public HUTuple2<Integer, HUTuple4<Integer, Integer, Integer, Integer>> apply(
                                                                                                                                                                                                                            HUTuple2<Integer, HUTuple4<Integer, Integer, Integer, Integer>> capsule,
                                                                                                                                                                                                                            HUTuple4<Integer, Integer, Integer, Integer> cur)
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                        HUTuple4<Integer, Integer, Integer, Integer> pre = capsule.el1();
                                                                                                                                                                                                                        int sum = capsule.el0();
                                                                                                                                                                                                                        if (pre != null)
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                            sum += Math.abs(cur.el1()
                                                                                                                                                                                                                                    - pre.el1())
                                                                                                                                                                                                                                    + Math.abs(cur.el2()
                                                                                                                                                                                                                                            - pre.el2())
                                                                                                                                                                                                                                    + Math.abs(cur.el3()
                                                                                                                                                                                                                                            - pre.el3());
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        return new HUTuple2<Integer, HUTuple4<Integer, Integer, Integer, Integer>>(
                                                                                                                                                                                                                                sum,
                                                                                                                                                                                                                                cur);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                };

    private static HUTuple2<Integer, HUTuple4<Integer, Integer, Integer, Integer>>                                                                                                               intervalSeedT4 = new HUTuple2<Integer, HUTuple4<Integer, Integer, Integer, Integer>>(
                                                                                                                                                                                                                        0,
                                                                                                                                                                                                                        null);

    private static HUFolder<HUTuple2<Integer, HUTuple5<Integer, Integer, Integer, Integer, Integer>>, HUTuple5<Integer, Integer, Integer, Integer, Integer>>                                     intervalT5     = new HUFolder<HUTuple2<Integer, HUTuple5<Integer, Integer, Integer, Integer, Integer>>, HUTuple5<Integer, Integer, Integer, Integer, Integer>>() {
                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                    public HUTuple2<Integer, HUTuple5<Integer, Integer, Integer, Integer, Integer>> apply(
                                                                                                                                                                                                                            HUTuple2<Integer, HUTuple5<Integer, Integer, Integer, Integer, Integer>> capsule,
                                                                                                                                                                                                                            HUTuple5<Integer, Integer, Integer, Integer, Integer> cur)
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                        HUTuple5<Integer, Integer, Integer, Integer, Integer> pre = capsule.el1();
                                                                                                                                                                                                                        int sum = capsule.el0();
                                                                                                                                                                                                                        if (pre != null)
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                            sum += Math.abs(cur.el1()
                                                                                                                                                                                                                                    - pre.el1())
                                                                                                                                                                                                                                    + Math.abs(cur.el2()
                                                                                                                                                                                                                                            - pre.el2())
                                                                                                                                                                                                                                    + Math.abs(cur.el3()
                                                                                                                                                                                                                                            - pre.el3())
                                                                                                                                                                                                                                    + Math.abs(cur.el4()
                                                                                                                                                                                                                                            - pre.el4());
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        return new HUTuple2<Integer, HUTuple5<Integer, Integer, Integer, Integer, Integer>>(
                                                                                                                                                                                                                                sum,
                                                                                                                                                                                                                                cur);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                };

    private static HUTuple2<Integer, HUTuple5<Integer, Integer, Integer, Integer, Integer>>                                                                                                      intervalSeedT5 = new HUTuple2<Integer, HUTuple5<Integer, Integer, Integer, Integer, Integer>>(
                                                                                                                                                                                                                        0,
                                                                                                                                                                                                                        null);

    private static HUFolder<HUTuple2<Integer, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>>, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>>                   intervalT6     = new HUFolder<HUTuple2<Integer, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>>, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>>() {
                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                    public HUTuple2<Integer, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>> apply(
                                                                                                                                                                                                                            HUTuple2<Integer, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>> capsule,
                                                                                                                                                                                                                            HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer> cur)
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                        HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer> pre = capsule.el1();
                                                                                                                                                                                                                        int sum = capsule.el0();
                                                                                                                                                                                                                        if (pre != null)
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                            sum += Math.abs(cur.el1()
                                                                                                                                                                                                                                    - pre.el1())
                                                                                                                                                                                                                                    + Math.abs(cur.el2()
                                                                                                                                                                                                                                            - pre.el2())
                                                                                                                                                                                                                                    + Math.abs(cur.el3()
                                                                                                                                                                                                                                            - pre.el3())
                                                                                                                                                                                                                                    + Math.abs(cur.el4()
                                                                                                                                                                                                                                            - pre.el4())
                                                                                                                                                                                                                                    + Math.abs(cur.el5()
                                                                                                                                                                                                                                            - pre.el5());
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        return new HUTuple2<Integer, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>>(
                                                                                                                                                                                                                                sum,
                                                                                                                                                                                                                                cur);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                };

    private static HUTuple2<Integer, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>>                                                                                             intervalSeedT6 = new HUTuple2<Integer, HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>>(
                                                                                                                                                                                                                        0,
                                                                                                                                                                                                                        null);

    private static HUFolder<HUTuple2<Integer, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> intervalT7     = new HUFolder<HUTuple2<Integer, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>() {
                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                    public HUTuple2<Integer, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> apply(
                                                                                                                                                                                                                            HUTuple2<Integer, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> capsule,
                                                                                                                                                                                                                            HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> cur)
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                        HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> pre = capsule.el1();
                                                                                                                                                                                                                        int sum = capsule.el0();
                                                                                                                                                                                                                        if (pre != null)
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                            sum += Math.abs(cur.el1()
                                                                                                                                                                                                                                    - pre.el1())
                                                                                                                                                                                                                                    + Math.abs(cur.el2()
                                                                                                                                                                                                                                            - pre.el2())
                                                                                                                                                                                                                                    + Math.abs(cur.el3()
                                                                                                                                                                                                                                            - pre.el3())
                                                                                                                                                                                                                                    + Math.abs(cur.el4()
                                                                                                                                                                                                                                            - pre.el4())
                                                                                                                                                                                                                                    + Math.abs(cur.el5()
                                                                                                                                                                                                                                            - pre.el5())
                                                                                                                                                                                                                                    + Math.abs(cur.el6()
                                                                                                                                                                                                                                            - pre.el6());
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        return new HUTuple2<Integer, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>(
                                                                                                                                                                                                                                sum,
                                                                                                                                                                                                                                cur);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                };

    private static HUTuple2<Integer, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>                                                                                    intervalSeedT7 = new HUTuple2<Integer, HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>(
                                                                                                                                                                                                                        0,
                                                                                                                                                                                                                        null);

}
