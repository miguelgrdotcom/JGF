package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import hu.list.tuple.HUTuple4;

import org.junit.Test;

public class HPCTuple4Test {
	int vala0 = 0;
	int vala1 = 1;
	int vala2 = 2;
	int vala3 = 3;
	HUTuple4<Integer, Integer, Integer, Integer> a = new HUTuple4<Integer, Integer, Integer, Integer>(vala0, vala1, vala2, vala3);

	@Test
	public void 初期化できるか() {
		assertThat(a.el0(), is(vala0));
		assertThat(a.el1(), is(vala1));
		assertThat(a.el2(), is(vala2));
		assertThat(a.el3(), is(vala3));
	}

	@Test
	public void 正しく比較できるか() {
		int valb0 = 0;
		int valb1 = 1;
		int valb2 = 2;
		int valb3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> b = new HUTuple4<Integer, Integer, Integer, Integer>(valb0, valb1, valb2, valb3);

		// 同じ 
		assertThat(a.compareTo(b), is(0));
		
		// aの方が小さい
		b = new HUTuple4<Integer, Integer, Integer, Integer>(10, valb1, valb2, valb3);
		assertThat(a.compareTo(b), is(-1));
		
		// aの方が大きい
		b = new HUTuple4<Integer, Integer, Integer, Integer>(-1, valb1, valb2, valb3);
		assertThat(a.compareTo(b), is(1));
	}
	
	@Test
	public void equalsがただしく動作しているか(){
		int valb0 = 0;
		int valb1 = 1;
		int valb2 = 2;
		int valb3 = 3;
		HUTuple4<Integer, Integer, Integer, Integer> b = new HUTuple4<Integer, Integer, Integer, Integer>(valb0, valb1, valb2, valb3);
		
		System.out.println(b);

		// 同じ 
		assertThat(a.equals(b), is(true));
		
		// aとbは等しくない
		b = new HUTuple4<Integer, Integer, Integer, Integer>(10, valb1, valb2, valb3);
		assertThat(a.equals(b), is(false));
	}
}
