package hu.list;

import hu.list.tuple.HUTuple1;
import hu.list.tuple.HUTuple2;
import hu.list.tuple.HUTuple3;
import hu.list.tuple.HUTuple4;
import hu.list.tuple.HUTuple5;
import hu.list.tuple.HUTuple6;
import hu.list.tuple.HUTuple7;

public class HUSetMaker
{
	/**
	 * 空集合を生成
	 * @return 空集合を表す HUSet インスタンス
	 */
    public static <U extends Comparable<U>> HUSet<U> getNull() {
        return new HUSet<U>();
    }

    /**
     * l1 の長さの辺２つが直角をなす直角三角形を底面とする高さl3の三角形を生成
     * @param l1
     * @param l3
     * @return 三角柱を表す HUSet インスタンス
     */
    public static HUSet<HUTuple3<Integer, Integer, Integer>> getTriangularPrism(int l1, int l3) {
        HUSet<HUTuple3<Integer, Integer, Integer>> set = new HUSet<HUTuple3<Integer,Integer,Integer>>();
        for (int i = 1; i <= l1; i++)
            for (int j = 1; j <= i-1; j++)
                for (int j2 = 1; j2 <= l3; j2++)
                    set.add(new HUTuple3<Integer, Integer, Integer>(i, j, j2));
        return set;
    }
    
    // 以下、機械生成. 
    public static HUSet<HUTuple1<Integer>> make1(int[] arr) { HUSet<HUTuple1<Integer>> set = new HUSet<HUTuple1<Integer>>();for(int i0 = arr[0]; i0 <= arr[1]; i0++)set.add(new HUTuple1<Integer>(i0)); return set;}
    public static HUSet<HUTuple2<Integer, Integer>> make2(int[] arr) { HUSet<HUTuple2<Integer, Integer>> set = new HUSet<HUTuple2<Integer, Integer>>();for(int i0 = arr[0]; i0 <= arr[1]; i0++)for(int i1 = arr[2]; i1 <= arr[3]; i1++)set.add(new HUTuple2<Integer, Integer>(i0, i1)); return set;}
    public static HUSet<HUTuple3<Integer, Integer, Integer>> make3(int[] arr) { HUSet<HUTuple3<Integer, Integer, Integer>> set = new HUSet<HUTuple3<Integer, Integer, Integer>>();for(int i0 = arr[0]; i0 <= arr[1]; i0++)for(int i1 = arr[2]; i1 <= arr[3]; i1++)for(int i2 = arr[4]; i2 <= arr[5]; i2++)set.add(new HUTuple3<Integer, Integer, Integer>(i0, i1, i2)); return set;}
    public static HUSet<HUTuple4<Integer, Integer, Integer, Integer>> make4(int[] arr) { HUSet<HUTuple4<Integer, Integer, Integer, Integer>> set = new HUSet<HUTuple4<Integer, Integer, Integer, Integer>>();for(int i0 = arr[0]; i0 <= arr[1]; i0++)for(int i1 = arr[2]; i1 <= arr[3]; i1++)for(int i2 = arr[4]; i2 <= arr[5]; i2++)for(int i3 = arr[6]; i3 <= arr[7]; i3++)set.add(new HUTuple4<Integer, Integer, Integer, Integer>(i0, i1, i2, i3)); return set;}
    public static HUSet<HUTuple5<Integer, Integer, Integer, Integer, Integer>> make5(int[] arr) { HUSet<HUTuple5<Integer, Integer, Integer, Integer, Integer>> set = new HUSet<HUTuple5<Integer, Integer, Integer, Integer, Integer>>();for(int i0 = arr[0]; i0 <= arr[1]; i0++)for(int i1 = arr[2]; i1 <= arr[3]; i1++)for(int i2 = arr[4]; i2 <= arr[5]; i2++)for(int i3 = arr[6]; i3 <= arr[7]; i3++)for(int i4 = arr[8]; i4 <= arr[9]; i4++)set.add(new HUTuple5<Integer, Integer, Integer, Integer, Integer>(i0, i1, i2, i3, i4)); return set;}
    public static HUSet<HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>> make6(int[] arr) { HUSet<HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>> set = new HUSet<HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>>();for(int i0 = arr[0]; i0 <= arr[1]; i0++)for(int i1 = arr[2]; i1 <= arr[3]; i1++)for(int i2 = arr[4]; i2 <= arr[5]; i2++)for(int i3 = arr[6]; i3 <= arr[7]; i3++)for(int i4 = arr[8]; i4 <= arr[9]; i4++)for(int i5 = arr[10]; i5 <= arr[11]; i5++)set.add(new HUTuple6<Integer, Integer, Integer, Integer, Integer, Integer>(i0, i1, i2, i3, i4, i5)); return set;}
    public static HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> make7(int[] arr) { HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> set = new HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>>();for(int i0 = arr[0]; i0 <= arr[1]; i0++)for(int i1 = arr[2]; i1 <= arr[3]; i1++)for(int i2 = arr[4]; i2 <= arr[5]; i2++)for(int i3 = arr[6]; i3 <= arr[7]; i3++)for(int i4 = arr[8]; i4 <= arr[9]; i4++)for(int i5 = arr[10]; i5 <= arr[11]; i5++)for(int i6 = arr[12]; i6 <= arr[13]; i6++)set.add(new HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>(i0, i1, i2, i3, i4, i5, i6)); return set;}
}
