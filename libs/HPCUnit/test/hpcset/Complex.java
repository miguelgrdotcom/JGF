package hpcset;

import java.io.Serializable;

public class Complex implements Serializable, Comparable<Complex>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7593574092899281049L;
	private final double re;
	private final double im;
	private int hashCode = 0;

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public double re() {
		return re;
	}

	public double im() {
		return im;
	}

	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}

	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	public Complex mul(Complex c) {
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}
	
	public Complex mul(Double d) {
		return new Complex(re * d, im * d);
	}

	public Complex div(Complex c) {
		double denominator = c.re * c.re + c.im * c.im;
		return new Complex((re * c.re + im * c.im) / denominator, (im * c.re - re * c.im) / denominator);
	}

	public Complex con() {
		return new Complex(re, -im);
	}

	public double abs() {
		return Math.sqrt(re * re + im * im);
	}

	public double arg() {
		return Math.atan(im / re);
	}

	public String toString() {
		if (im >= 0)
			return "(" + re + " + " + im + "i" + ")";
		else
			return "(" + re + " - " + -im + "i" + ")";
	}

	@Override
	public int compareTo(Complex o) {
		return (int)(this.re != o.re ? this.re > o.re ? 1 : -1 : 
			this.im == o.im ? 0 : this.im > o.im ? 1 : -1);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Complex))
			return false;
		else {
			Complex c = (Complex)o;
			return this.re == c.re && this.im == c.im; 
		}
	}
	
	@Override
	public int hashCode() {
		if (hashCode == 0) {
			int result = 17;
			result = 37 * result + new Double(re).hashCode();
			result = 37 * result + new Double(im).hashCode();
			hashCode = result;
		}
		return hashCode;
	}
}