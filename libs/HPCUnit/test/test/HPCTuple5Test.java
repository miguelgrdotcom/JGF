package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import hu.list.HUMapper;
import hu.list.tuple.HUTuple4;
import hu.list.tuple.HUTuple5;

import org.junit.Test;

public class HPCTuple5Test {

	int vala0 = 0;
	int vala1 = 1;
	int vala2 = 2;
	int vala3 = 3;
	int vala4 = 4;
	HUTuple5<Integer, Integer, Integer, Integer, Integer> a = new HUTuple5<Integer, Integer, Integer, Integer, Integer>(vala0, vala1, vala2, vala3, vala4);

	@Test
	public void 初期化できるか() {
		assertThat(a.el0(), is(vala0));
		assertThat(a.el1(), is(vala1));
		assertThat(a.el2(), is(vala2));
		assertThat(a.el3(), is(vala3));
		assertThat(a.el4(), is(vala4));
	}

	@Test
	public void 正しく比較できるか() {
		int valb0 = 0;
		int valb1 = 1;
		int valb2 = 2;
		int valb3 = 3;
		int valb4 = 4;
		HUTuple5<Integer, Integer, Integer, Integer, Integer> b = new HUTuple5<Integer, Integer, Integer, Integer, Integer>(valb0, valb1, valb2, valb3, valb4);

		// 同じ 
		assertThat(a.compareTo(b), is(0));
		
		// aの方が小さい
		b = new HUTuple5<Integer, Integer, Integer, Integer, Integer>(10, valb1, valb2, valb3, valb4);
		assertThat(a.compareTo(b), is(-1));

		// aの方が大きい
		b = new HUTuple5<Integer, Integer, Integer, Integer, Integer>(-1, valb1, valb2, valb3, valb4);
		assertThat(a.compareTo(b), is(1));
	}

	@Test
	public void 射影が正しく動作するか() {
		HUTuple4<Integer, Integer, Integer, Integer> newTuple = a.map(new HUMapper<HUTuple5<Integer,Integer,Integer,Integer,Integer>, HUTuple4<Integer,Integer,Integer,Integer>>() {
			public  HUTuple4<Integer,Integer,Integer,Integer> apply(HUTuple5<Integer,Integer,Integer,Integer,Integer> o) {
				HUTuple4<Integer, Integer, Integer, Integer> newTuple = new HUTuple4<Integer, Integer, Integer, Integer>(o.el1(),o.el2(),o.el3(),o.el4());
				return newTuple;
			}
		});
		
		assertThat(newTuple.el0(), is(a.el1()));
		assertThat(newTuple.el1(), is(a.el2()));
		assertThat(newTuple.el2(), is(a.el3()));
		assertThat(newTuple.el3(), is(a.el4()));
	}
}
