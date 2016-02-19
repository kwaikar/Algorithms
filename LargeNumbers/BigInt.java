import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Input number
 * 
 * @author Kanchan Waikar Date Created : 7:30:41 PM
 *
 */
public class BigInt {

	List<Integer> coefficients = null;
	public static final int BASE = 10;
	public boolean isPositive = true;

	BigInt(List<Integer> coefficients) {
		super();
		this.coefficients = coefficients;
	}

	public BigInt clone() {
		BigInt clone = new BigInt();
		clone.isPositive = this.isPositive;
		clone.coefficients = new ArrayList<Integer>();
		for (Integer integer : coefficients) {
			clone.coefficients.add(integer);
		}
		return clone;
	}

	/**
	 * Constructor for XYZ class; takes a string s as parameter, that stores a
	 * number in decimal, and creates the XYZ object representing that number.
	 * The string can have arbitrary length. In Level 1, the string contains
	 * only the numerals 0-9, with no leading zeroes.
	 * 
	 * @param input
	 *            - String representation of the input.
	 */
	public BigInt(String input) {

		if (input != null && input.trim().length() > 0) {
			/**
			 * cleanup spaces
			 */
			input = input.replaceAll(" ", "").trim();
			/**
			 * Identify sign of the input
			 */
			if (input.startsWith("-")) {
				isPositive = false;
			}
			/**
			 * Replace the sign.
			 */
			input = input.replaceFirst("-", "").trim();
			/**
			 * Validate input!
			 */
			if (input.length() == 0) {
				System.out.println("Invalid input");
			} else {
				/**
				 * Valid input. remove first zeros.
				 */
				while (input.length()>0 && input.charAt(0) == '0') {
					input = input.replaceFirst("0", "");
				}
				if (input.length() > 0) {
					coefficients = new ArrayList<Integer>(input.length());
					for (int i = 0; i < input.length(); i++) {
						coefficients.add(i, Integer.parseInt(input.charAt(input.length() - 1 - i) + ""));
					}
				} else {
					isPositive = true;
					coefficients = new ArrayList<Integer>(1);
					coefficients.add(0, 0);

				}
			}
		} else {
			System.out.println("Empty input string");
		}
	}

	public BigInt(Long num) {
		this(num.toString());
	}

	public BigInt() {
		super();
	}

	/**
	 * This Static method accepts two numbers and adds up both of them.
	 * 
	 * @param num1
	 *            - Number 1
	 * @param num2
	 *            - Number 2
	 * @return
	 */
	public static BigInt add(BigInt num1, BigInt num2) {
		BigInt output = new BigInt();
		System.out.println("adding" + num1 + ":" + num2);
		if (!num2.isPositive && !num1.isPositive) {
			output.isPositive = false;
		} else if (!num2.isPositive) {
			num2.isPositive = true;
			output = subtract(num1, num2);
			num2.isPositive = false;
			return output;
		} else if (!num1.isPositive) {
			num1.isPositive = true;
			output = subtract(num1, num2);
			num1.isPositive = false;

		}

		output.coefficients = new ArrayList<Integer>((num1.coefficients.size() > num2.coefficients.size()
				? num1.coefficients.size() : num2.coefficients.size()) + 1);
		/**
		 * i - Counter for looping through coefficient arrays carry - Boolean
		 * flag for checking whether carry is set or not. unfinishedList -
		 * Reference to the Larger list.
		 */
		int i = 0;
		boolean carry = false;

		while (i < num1.coefficients.size() && i < num2.coefficients.size()) {
			/**
			 * Add both common coefficients
			 */
			int temp = num1.coefficients.get(i) + num2.coefficients.get(i) + (carry ? 1 : 0);
			if (temp >= BASE) {
				output.coefficients.add(i++, temp % BASE);
				carry = true;
			} else {
				output.coefficients.add(i++, temp);
				carry = false;
			}
		}

		List<Integer> unfinishedList = (i != num1.coefficients.size()) ? num1.coefficients : num2.coefficients;

		while (i != unfinishedList.size()) {
			/**
			 * Loop over the larger number and add the coefficients after
			 * considering the carry to the output.
			 */
			int temp = unfinishedList.get(i) + (carry ? 1 : 0);
			if (temp >= BASE) {
				output.coefficients.add(i++, temp % BASE);
				carry = true;
			} else {
				output.coefficients.add(i++, temp);
				carry = false;
			}
		}
		if (carry) {
			output.coefficients.add(i++, 1);
		}
		return output;
	}

	/**
	 * This method returns whether both numbers are equal or not.
	 */
	@Override
	public boolean equals(Object obj) {
		BigInt number = (BigInt) obj;
		return this.toString().equalsIgnoreCase(number.toString());
	}

	/**
	 * This Static method accepts two numbers and subtracts right from left.
	 * 
	 * @param num1Main
	 *            - Number 1
	 * @param num2Main
	 *            - Number 2
	 * @return
	 */
	public static BigInt subtract(BigInt num1Main, BigInt num2Main) {
		BigInt output = new BigInt();
		BigInt num1 = num1Main.clone();
		BigInt num2 = num2Main.clone();

		if (num1.isPositive && !num2.isPositive) {
			num2.isPositive = true;
			output = add(num1, num2);
			num2.isPositive = false;
			return output;
		} else if (num2.isPositive && !num1.isPositive) {
			num1.isPositive = true;
			output = add(num1, num2);
			output.isPositive = false;
			num1.isPositive = false;
			return output;
		} else if (!num1.isPositive && !num2.isPositive) {
			num2.isPositive = true;
			output = num1;
			num1 = num2;
			num2 = output;
			num2.isPositive = true;
		}
		System.out.println(num1 + ":" + num2 + ":" + num1Main + ":" + num2Main);
		/**
		 * Find larger number and then subtract smaller from larger.
		 */
		if (num1.coefficients.size() == num2.coefficients.size()) {
			int counter = num2.coefficients.size() - 1;
			/**
			 * num1 should contain bigger number and num2 should contain smaller
			 * number
			 */
			System.out.println(num1 + ":" + num2);
			boolean flagEquals = true;
			while (counter >= 0) {
				if (num1.coefficients.get(counter) > num2.coefficients.get(counter)) {

					flagEquals = false;
				} else if (flagEquals && num2.coefficients.get(counter) > num1.coefficients.get(counter)) {
					/**
					 * num2 is greater than num 1.
					 */
					BigInt temp = num2;
					num2 = num1;
					num1 = temp;
					temp = null;
					output.isPositive = !output.isPositive;
					counter = -1;
					flagEquals = false;
					break;
				}
				counter--;
			}


			if (flagEquals) {
				return new BigInt(0L);
			}
		} else if (num2.coefficients.size() > num1.coefficients.size()) {
			/**
			 * Swaps should lead to sign reversal
			 */
			BigInt temp = num2;
			num2 = num1;
			num1 = temp;
			temp = null;
			output.isPositive = !output.isPositive;
		}

		if (!num2.isPositive) {
			/**
			 * num1 is negative too, and num1 is smaller than num2, sign needs
			 * to be reversed!
			 */
			output.isPositive = !output.isPositive;
		}

		output.coefficients = unsignedSubtract(num1, num2);
		/**
		 * Take care of numbers with leading zeros.
		 */
		int counter = output.coefficients.size() - 1;
		while (output.coefficients.get(counter) == 0 && counter != 0) {
			output.coefficients.remove(counter--);
		}
		if (output.coefficients.size() == 1 && output.coefficients.get(0) == 0) {
			output.isPositive = true;
		}

		return output;
	}

	/**
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static List<Integer> unsignedSubtract(BigInt num1, BigInt num2) {
		List<Integer>coefficients = new ArrayList<Integer>((num1.coefficients.size() > num2.coefficients.size()
				? num1.coefficients.size() : num2.coefficients.size()));
		/**
		 * i - Counter for looping through coefficient arrays carry - Boolean
		 * flag for checking whether carry is set or not. unfinishedList -
		 * Reference to the Larger list.
		 */
		int i = 0;
		boolean loan = false;

		while (i < num1.coefficients.size() && i < num2.coefficients.size()) {
			/**
			 * subtract common coefficients
			 */
			int valueToBeSubtracted = num2.coefficients.get(i) + (loan ? 1 : 0);
			if (valueToBeSubtracted > num1.coefficients.get(i)) {
				loan = true;
				// Loan amount equal to the base.
				coefficients.add(i, ((num1.coefficients.get(i) + BASE) - num2.coefficients.get(i)));
			} else {
				coefficients.add(i, ((num1.coefficients.get(i)) - valueToBeSubtracted));
				loan = false;
			}
			i++;
		}

		while (i != num1.coefficients.size()) {
			/**
			 * Loop over the larger number and add the coefficients after
			 * considering the loan.
			 */
			int temp = num1.coefficients.get(i) - (loan ? 1 : 0);
			if (temp < 0) {
				coefficients.add(i++, temp + BASE);
				loan = true;
			} else {
				coefficients.add(i++, temp);
				loan = false;
			}
		}
		System.out.println(num1+"-"+num2+"="+coefficients);
		return coefficients;
	}

	public static BigInt multiply(BigInt num1, BigInt num2) {
		System.out.println(num1 + "*" + num2);
		BigInt output = new BigInt();
		if ((num1.coefficients.size() == 1 && num1.coefficients.get(0) == 0)
				|| (num2.coefficients.size() == 1 && num2.coefficients.get(0) == 0)) {
			output.coefficients.add(0, 0);
			return output;
		}
		if (!(num1.isPositive == num2.isPositive)) {
			output.isPositive = false;
		}
		output.coefficients = unsignedMultiple(num1, num2).coefficients;
		System.out.println("|" + output + "|");
		return output;
	}

	public BigInt shift(int n) {
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				this.coefficients.add(0, 0);
			}
		}
		return this;
	}

	/**
	 * Implementation of karatsuba algorithm for Multiplication
	 * 
	 * @param num1
	 * @param num2
	 * @param base
	 * @return
	 */
	private static BigInt unsignedMultiple(BigInt num1, BigInt num2) {
		System.out.println(num1 + "__" + num2);

		if (num1.coefficients.size() == 0 || num2.coefficients.size() == 0) {
			return new BigInt(0L);
		} else if (num1.coefficients.size() == 1 && num2.coefficients.size() == 1) {
			System.out.println("entered");
			BigInt output = new BigInt();
			int temp = num1.coefficients.get(0) * num2.coefficients.get(0);
			if (temp > BASE) {
				output.coefficients = new ArrayList<Integer>(2);
				output.coefficients.add(0, temp % BASE);
				output.coefficients.add(1, (int) Math.floor(temp / BASE));
			} else {
				output.coefficients = new ArrayList<Integer>(1);
				output.coefficients.add(0, temp);
			}
			return output;
		} else if (num1.coefficients.size() == 1 || num2.coefficients.size() == 1) {
			if (num1.coefficients.size() > num2.coefficients.size()) {
				BigInt al = new BigInt(new ArrayList<Integer>(num1.coefficients.subList(0, 1)));
				BigInt am = new BigInt(new ArrayList<Integer>(num1.coefficients.subList(1, num1.coefficients.size())));
				BigInt bl = new BigInt(new ArrayList<Integer>(num2.coefficients.subList(0, 1)));
				return BigInt.add(unsignedMultiple(am, bl).shift(1), unsignedMultiple(al, bl));
			} else {
				BigInt al = new BigInt(new ArrayList<Integer>(num1.coefficients.subList(0, 1)));
				BigInt bm = new BigInt(new ArrayList<Integer>(num2.coefficients.subList(1, num2.coefficients.size())));
				BigInt bl = new BigInt(new ArrayList<Integer>(num2.coefficients.subList(0, 1)));
				return BigInt.add(unsignedMultiple(al, bm).shift(1), unsignedMultiple(al, bl));
			}
		} else {
			int k = (int) Math.floor((((num1.coefficients.size() > num2.coefficients.size())
					? num1.coefficients.size() / 2 : num2.coefficients.size() / 2)));

			BigInt al = new BigInt(new ArrayList<Integer>(new ArrayList<Integer>(num1.coefficients.subList(0, k))));
			BigInt bl = new BigInt(new ArrayList<Integer>(new ArrayList<Integer>(num2.coefficients.subList(0, k))));

			BigInt am = new BigInt(new ArrayList<Integer>(
					new ArrayList<Integer>(num1.coefficients.subList(k, num1.coefficients.size()))));
			BigInt bm = new BigInt(new ArrayList<Integer>(
					new ArrayList<Integer>(num2.coefficients.subList(k, num2.coefficients.size()))));

			BigInt albl = unsignedMultiple(al, bl);
			BigInt ambm = unsignedMultiple(am, bm);
			BigInt abMid = unsignedMultiple(BigInt.add(al, am), BigInt.add(bl, bm));
			BigInt ab = BigInt.subtract(BigInt.subtract(abMid, albl), ambm);
			if (num1.coefficients.size() == num2.coefficients.size()) {
				System.out.println("==>" + num1 + ":" + num2 + " : " + k);
				System.out.println("Computing ab:" + BigInt.add(al, am) + " * " + BigInt.add(bl, bm) + "=" + abMid + "-"
						+ albl + "-" + ambm);
				System.out.println("BigInt.subtract(abMid, albl)=" + BigInt.subtract(abMid, albl));
				BigInt temp = BigInt.subtract(abMid, albl);
				System.out.println("temp-" + temp + "---" + BigInt.subtract(temp, ambm));
				System.out.println("BigInt.subtract(BigInt.subtract(abMid, albl),ambm)"
						+ BigInt.subtract(BigInt.subtract(abMid, albl), ambm));
				System.out.println("ab=" + ab);

				ab.shift(k);
				ambm.shift((2 * k));
				System.out.println(
						"====>" + (al) + "*" + bl + "=" + albl + "," + (am) + "*" + bm + "=" + ambm + " : " + ab);
			} else {
				ab.shift(k);
				ambm.shift(2 * k);
			}

			// System.out.println((al)+"*"+bl+"="+albl+ ","+(am)+"*"+bm+"=" +
			// ambm+ " : " + ab);

			return BigInt.add(BigInt.add(albl, ab), ambm);

		}
	}

	/**
	 * String toString(): convert the XYZ class object into its equivalent
	 * string (in decimal). There should be no leading zeroes in the string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Integer integer : coefficients) {
			sb.append(integer);
		}
		return this.isPositive ? sb.reverse().toString() : "-" + sb.reverse().toString();
	}

	public static void main(String[] args) {
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		loader.setDefaultAssertionStatus(true);
		// assertCustom((BigInt.subtract(new BigInt(-9L), new
		// BigInt(-6L))),"-3");

		assertCustom((BigInt.subtract(new BigInt(-6L), new BigInt(-9L))), "3");

		assertCustom((BigInt.subtract(new BigInt(9L), new BigInt(-6L))), "15");
		assertCustom((BigInt.subtract(new BigInt(6L), new BigInt(-9L))), "15");
		assertCustom((BigInt.subtract(new BigInt(6L), new BigInt(9L))), "-3");
		BigInt xyz = new BigInt("004268");
		assertCustom(xyz, "4268");
		assertCustom(new BigInt(4268L), "4268");
		assertCustom(new BigInt(new Long(4268)), "4268");
		assertCustom((BigInt.add(new BigInt(999L), new BigInt(1L))), "1000");
		assertCustom((BigInt.add(new BigInt(99L), new BigInt(99L))), "198");
		assertCustom((BigInt.add(new BigInt(-99L), new BigInt(-99L))), "-198");
		assertCustom(BigInt.subtract(new BigInt(99L), new BigInt(99L)), "0");
		assertCustom((BigInt.subtract(new BigInt(99L), new BigInt(99L))), "0");
		assertCustom((BigInt.subtract(new BigInt(99L), new BigInt(-99L))), "198");
		assertCustom((BigInt.subtract(new BigInt(-99L), new BigInt(99L))), "-198");
		assertCustom((BigInt.subtract(new BigInt(-99L), new BigInt(-99L))), "0");
		assertCustom(BigInt.multiply(new BigInt(11L), new BigInt(11L)), "121");
		assertCustom(BigInt.multiply(new BigInt(71L), new BigInt(38L)), "2698");

		assertCustom((BigInt.subtract(new BigInt(80L), new BigInt(21L))), "59");
		assertCustom(BigInt.multiply(new BigInt(11L), new BigInt(5L)), "55");
		assertCustom(BigInt.multiply(new BigInt(1L), new BigInt(1L)), "1");
		assertCustom(BigInt.multiply(new BigInt(12345L), new BigInt(6789L)), "83810205");
		assertCustom(BigInt.multiply(new BigInt(12345L), new BigInt(6789L)), "8381205");
		System.out.println(BigInt.multiply(new BigInt(12345L), new BigInt(6789L)));
	}

	public static void assertCustom(BigInt value, String check) {
		if (!value.toString().equals(check)) {
			System.out.println("Msmatch: " + value + " : expected : " + check);
			throw new AssertionError();
		}
	}
}