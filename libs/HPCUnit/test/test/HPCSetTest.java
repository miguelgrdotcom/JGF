package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import hpcset.Complex;
import hu.list.HUMapper;
import hu.list.HUSet;
import hu.list.tuple.HUTuple4;
import hu.list.tuple.HUTuple5;
import hu.list.tuple.HUTuple6;
import hu.list.tuple.HUTuple7;

import org.junit.Test;

public class HPCSetTest {
	HUSet<HUTuple4<Integer, Integer, Integer, Integer>> A = new HUSet<HUTuple4<Integer, Integer, Integer, Integer>>();
	HUSet<HUTuple4<Integer, Integer, Integer, Integer>> B = new HUSet<HUTuple4<Integer, Integer, Integer, Integer>>();
	HUSet<HUTuple4<Integer, Integer, Integer, Integer>> C = new HUSet<HUTuple4<Integer, Integer, Integer, Integer>>();

	@Test
	public void 初期化できているか() {
		assertThat(A.size(), is(0));
		assertThat(A.isEmpty(), is(true));
	}

	@Test
	public void 要素の追加() {
		int val0 = 0;
		int val1 = 1;
		int val2 = 2;
		int val3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sample = new HUTuple4<Integer, Integer, Integer, Integer>(val0, val1, val2, val3);
		A.add(sample);

		assertThat(A.size(), is(1));
		assertThat(A.isEmpty(), is(false));
	}

	@Test
	public void 集合比較() {
		int val0 = 0;
		int val1 = 1;
		int val2 = 2;
		int val3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sample = new HUTuple4<Integer, Integer, Integer, Integer>(val0, val1, val2, val3);
		A.add(sample);

		int valB0 = 1;
		int valB1 = 1;
		int valB2 = 2;
		int valB3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sampleB = new HUTuple4<Integer, Integer, Integer, Integer>(valB0, valB1, valB2,
				valB3);
		B.add(sampleB);

		// 等しくない集合の比較　
		assertThat(A.equals(B), is(false));

		int valC0 = 0;
		int valC1 = 1;
		int valC2 = 2;
		int valC3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sampleC = new HUTuple4<Integer, Integer, Integer, Integer>(valC0, valC1, valC2,
				valC3);
		C.add(sampleC);

		// 等しい集合の比較　
		assertThat(A.equals(C), is(true));
	}

	@Test
	public void 和集合() {
		int val0 = 0;
		int val1 = 1;
		int val2 = 2;
		int val3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sample = new HUTuple4<Integer, Integer, Integer, Integer>(val0, val1, val2, val3);
		A.add(sample);

		int valB0 = 1;
		int valB1 = 1;
		int valB2 = 2;
		int valB3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sampleB = new HUTuple4<Integer, Integer, Integer, Integer>(valB0, valB1, valB2,
				valB3);
		B.add(sampleB);

		// Bの要素が追加される
		HUSet<HUTuple4<Integer, Integer, Integer, Integer>> AB = A.union(B);
		assertThat(AB.size(), is(2));
		assertThat(AB.isEmpty(), is(false));

		int valC0 = 0;
		int valC1 = 1;
		int valC2 = 2;
		int valC3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sampleC = new HUTuple4<Integer, Integer, Integer, Integer>(valC0, valC1, valC2,
				valC3);
		C.add(sampleC);

		// 重複する要素も追加される
		HUSet<HUTuple4<Integer, Integer, Integer, Integer>> AC = A.union(C);
		assertThat(AC.size(), is(2));
		assertThat(AC.isEmpty(), is(false));
	}

	@Test
	public void 積集合() {
		int val0 = 0;
		int val1 = 1;
		int val2 = 2;
		int val3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sample = new HUTuple4<Integer, Integer, Integer, Integer>(val0, val1, val2, val3);
		A.add(sample);

		int valB0 = 1;
		int valB1 = 1;
		int valB2 = 2;
		int valB3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sampleB = new HUTuple4<Integer, Integer, Integer, Integer>(valB0, valB1, valB2,
				valB3);
		B.add(sampleB);

		// 共通部分がない
		HUSet<HUTuple4<Integer, Integer, Integer, Integer>> AB = A.intersection(B);
		assertThat(AB.size(), is(0));
		assertThat(AB.isEmpty(), is(true));

		int valC0 = 0;
		int valC1 = 1;
		int valC2 = 2;
		int valC3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sampleC = new HUTuple4<Integer, Integer, Integer, Integer>(valC0, valC1, valC2,
				valC3);
		C.add(sampleC);

		// 共通部分がある
		HUSet<HUTuple4<Integer, Integer, Integer, Integer>> AC = A.intersection(C);
		assertThat(AC.size(), is(1));
		assertThat(AC.isEmpty(), is(false));
	}
	
	@Test
	public void 差集合() {
		int val0 = 0;
		int val1 = 1;
		int val2 = 2;
		int val3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sample = new HUTuple4<Integer, Integer, Integer, Integer>(val0, val1, val2, val3);
		A.add(sample);

		int valB0 = 1;
		int valB1 = 1;
		int valB2 = 2;
		int valB3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sampleB = new HUTuple4<Integer, Integer, Integer, Integer>(valB0, valB1, valB2,
				valB3);
		B.add(sampleB);

		// 共通部分がない
		HUSet<HUTuple4<Integer, Integer, Integer, Integer>> AB = A.difference(B);
		assertThat(AB.size(), is(1));
		assertThat(AB.isEmpty(), is(false));

		int valC0 = 0;
		int valC1 = 1;
		int valC2 = 2;
		int valC3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> sampleC = new HUTuple4<Integer, Integer, Integer, Integer>(valC0, valC1, valC2,
				valC3);
		C.add(sampleC);

		// 共通部分がある
		HUSet<HUTuple4<Integer, Integer, Integer, Integer>> AC = A.difference(C);
		assertThat(AC.size(), is(0));
		assertThat(AC.isEmpty(), is(true));
	}
	
	public void 空集合() {
	   // HPCList<HPCTuple1<Integer>> nullSA = HPCList.getNull();
	}

//	@Test
//	public void 写像() {
//		int val0 = 0;
//		int val1 = 1;
//		int val2 = 2;
//		int val3 = 3;
//		HPCTuple4<Integer, Integer, Integer, Integer> sample = new HPCTuple4<Integer, Integer, Integer, Integer>(val0, val1, val2, val3);
//		A.add(sample);
//
//		// 要素を２倍
//		HPCList<HPCTuple4<Integer, Integer, Integer, Integer>> mapA = A.map(new HPCMapper<HPCTuple4<Integer, Integer, Integer, Integer>>() {
//			@Override
//			public HPCTuple4<Integer, Integer, Integer, Integer> calc(HPCTuple4<Integer, Integer, Integer, Integer> o) {
//				return new HPCTuple4<Integer, Integer, Integer, Integer>(o.get0() * 2, o.get1() * 2, o.get2() * 2, o.get3() * 2);
//			}
//		});
//
//		for (HPCTuple4<Integer, Integer, Integer, Integer> hpcTuple4 : mapA) {
//			assertThat(hpcTuple4.get0(), is(val0 * 2));
//			assertThat(hpcTuple4.get1(), is(val1 * 2));
//			assertThat(hpcTuple4.get2(), is(val2 * 2));
//			assertThat(hpcTuple4.get3(), is(val3 * 2));
//		}
//	}

	@Test
	public void RSDFT() {
		
		int size1 = 100;
		int size2 = 200;
		int size3 = 2;
		int size4 = 2;
		
		// 集合の生成
		int cnt_normal=0;
		HUSet<HUTuple6<Integer, Integer, Integer, Integer, Integer, Complex>> normal = new HUSet<HUTuple6<Integer, Integer, Integer, Integer, Integer, Complex>>();
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				for (int j2 = 0; j2 < size3; j2++) {
					for (int k = 0; k < size4; k++) {
						normal.add(new HUTuple6<Integer, Integer, Integer, Integer, Integer, Complex>(
								cnt_normal++,
								i,
								j,
								j2,
								k,
								new Complex(i, j)
								));
					}
				}
			}
		}
		
		int cnt_optimized = 0;
		HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>> optimizedZgemv = new HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>>();
		for (int i = 0; i < size1/2; i++) {
			for (int j = 0; j < size2; j++) {
				for (int j2 = 0; j2 < size3; j2++) {
					for (int k = 0; k < size4; k++) {
						optimizedZgemv.add(new HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>(
								cnt_optimized++,
								0,
								i,
								j,
								j2,
								k,
								new Complex(i, j)
								));
					}
				}
			}
		}
		
		HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>> optimizedZgemm = new HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>>();

		for (int i = size1/2; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				for (int j2 = 0; j2 < size3; j2++) {
					for (int k = 0; k < size4; k++) {
						optimizedZgemm.add(new HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>(
								cnt_optimized++,
								0,
								i,
								j,
								j2,
								k,
								new Complex(i, j)
								));
					}
				}
			}
		}
	
		// normal, optimizedの作成 (normalはそのまま)
		HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>> optimized_raw = optimizedZgemv.union(optimizedZgemm);
		
		HUSet<HUTuple6<Integer, Integer, Integer, Integer, Integer, Complex>> optimized = optimized_raw.map(new HUMapper<HUTuple7<Integer,Integer,Integer,Integer,Integer,Integer,Complex>,HUTuple6<Integer,Integer,Integer,Integer,Integer,Complex> >() {
			public HUTuple6<Integer,Integer,Integer,Integer,Integer,Complex> apply(HUTuple7<Integer,Integer,Integer,Integer,Integer,Integer,Complex> o) {
				return new HUTuple6<Integer, Integer, Integer, Integer, Integer, Complex>(
						o.el0(),
						o.el1()*100+o.el2(),
						o.el3(),
						o.el4(),
						o.el5(),
						o.el6()
						);
			}
		});
		
		//////////// 計算領域の比較 
		
		// 範囲を限定(値は除外)
		HUSet<HUTuple5<Integer, Integer, Integer, Integer, Integer>> normal1 = normal.map(new HUMapper<HUTuple6<Integer,Integer,Integer,Integer,Integer,Complex>,HUTuple5<Integer, Integer,Integer,Integer,Integer>>() {
			public HUTuple5<Integer, Integer,Integer,Integer,Integer> apply(HUTuple6<Integer,Integer,Integer,Integer,Integer,Complex> o) {
				return new HUTuple5<Integer, Integer, Integer, Integer, Integer>(
						o.el0(),
						o.el1(),
						o.el2(),
						o.el3(),
						o.el4()
						);
			     }
		});
		
		HUSet<HUTuple5<Integer, Integer, Integer, Integer, Integer>> optimized1 = optimized.map(new HUMapper<HUTuple6<Integer,Integer,Integer,Integer,Integer,Complex>,HUTuple5<Integer, Integer,Integer,Integer,Integer>>() {
			public HUTuple5<Integer, Integer,Integer,Integer,Integer> apply(HUTuple6<Integer,Integer,Integer,Integer,Integer,Complex> o) {
				return new HUTuple5<Integer, Integer, Integer, Integer, Integer>(
						o.el0(),
						o.el1(),
						o.el2(),
						o.el3(),
						o.el4()
						);
			     }
		});
		
		// 集合として比較する際には、追加した順序の情報を削除
		HUSet<HUTuple4<Integer, Integer, Integer, Integer>> normal2 = normal1.map(new HUMapper<HUTuple5<Integer,Integer,Integer,Integer,Integer>,HUTuple4<Integer,Integer,Integer,Integer>>() {
			public HUTuple4<Integer,Integer,Integer,Integer> apply(HUTuple5<Integer,Integer,Integer,Integer,Integer> o) {
				return new HUTuple4<Integer, Integer, Integer, Integer>(
						o.el0(),
						o.el1(),
						o.el2(),
						o.el3()
						);
			     }
		});
		
		HUSet<HUTuple4<Integer, Integer, Integer, Integer>> optimized2 = optimized1.map(new HUMapper<HUTuple5<Integer,Integer,Integer,Integer,Integer>,HUTuple4<Integer,Integer,Integer,Integer>>() {
			public HUTuple4<Integer,Integer,Integer,Integer> apply(HUTuple5<Integer,Integer,Integer,Integer,Integer> o) {
				return new HUTuple4<Integer, Integer, Integer, Integer>(
						o.el0(),
						o.el1(),
						o.el2(),
						o.el3()
						);
			     }
		});
		
		assertThat(normal2.equals(optimized2), is(true));
		
		//////////// gemvとgemmで重複はないか
		HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>> intersection = optimizedZgemv.intersection(optimizedZgemm);
		HUSet<HUTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Complex>> empty = new HUSet<HUTuple7<Integer,Integer,Integer,Integer,Integer,Integer,Complex>>();
		
		assertThat(empty.equals(intersection), is(true));
	}
}
