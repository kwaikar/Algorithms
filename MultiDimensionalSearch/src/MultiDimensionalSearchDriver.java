
import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Sample driver code for Project LP4. Modify as needed. Do not change
 * input/output formats.
 * 
 * @author rbk
 */
public class MultiDimensionalSearchDriver {
	static long[] description;
	static final int DLENGTH = 100000;
	public static DecimalFormat df = new DecimalFormat("#.00");

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			in = new Scanner(new File(args[0]));
		} else {
			in = new Scanner(System.in);
		}
		String s;
		long total = 0;
		description = new long[DLENGTH];
		Statistics stats = new Statistics();
		stats.timer();
		MultiDimensionalSearch mds = new MultiDimensionalSearch();
		int counter = 0;
		while (in.hasNext()) {
			long rv = 0;
			counter++;

			s = in.next();
			if (s.charAt(0) == '#') {
				s = in.nextLine();
				continue;
			}
			if (s.equals("Insert")) {

				long id = in.nextLong();
				double price = in.nextDouble();
				long des = in.nextLong();
				int index = 0;
				while (des != 0) {
					description[index++] = des;
					des = in.nextInt();
				}
				description[index] = 0;
				rv += 100*mds.insert(id, price, Arrays.copyOfRange(description, 0, index), index);

			} else if (s.equals("Find")) {
				long id = in.nextLong();
				rv += MultiDimensionalSearch.convertToCents (mds.find(id));
			} else if (s.equals("Delete")) {
				long id = in.nextLong();
				rv +=100* (mds.delete(id));
			} else if (s.equals("FindMinPrice")) {
				long des = in.nextLong();
				rv += MultiDimensionalSearch.convertToCents (mds.findMinPrice(des));
			} else if (s.equals("FindMaxPrice")) {
				long des = in.nextLong();
				rv += MultiDimensionalSearch.convertToCents (mds.findMaxPrice(des));
			} else if (s.equals("FindPriceRange")) {
				long des = in.nextLong();
				double lowPrice = in.nextDouble();
				double highPrice = in.nextDouble();
				rv += 100*mds.findPriceRange(des, lowPrice, highPrice);
			} else if (s.equals("PriceHike")) {
				long minid = in.nextLong();
				long maxid = in.nextLong();
				double rate = in.nextDouble();
				rv += MultiDimensionalSearch.convertToCents (mds.priceHike(minid, maxid, rate));
			} else if (s.equals("Range")) {
				double lowPrice = in.nextDouble();
				double highPrice = in.nextDouble();
				rv += 100*(mds.range(lowPrice, highPrice));
			} else if (s.equals("SameSame")) {
				rv +=  100* (mds.samesame());
			} else if (s.equals("End")) {
				break;
			} else {
				System.out.println("Houston, we have a problem.\nUnexpected line in input: " + s);
				System.exit(0);
			}
			/*
			 * Following portion needs to be uncommented for printing the detailed output of the program.
			 * 
			 */
			/*String rvb = ((double)rv/100) + "";
			rvb = rvb.endsWith("0") ? rvb.substring(0, rvb.length() - 2) : rvb;
			rvb = rvb.charAt(rvb.length() - 1) == '.' ? rvb.substring(0, rvb.length() - 1) : rvb;

			if (s.equalsIgnoreCase("find") ||s.equalsIgnoreCase("findMaxPrice")||s.equalsIgnoreCase("findMinPrice")) {
				String str = df.format(((double)rv/100));
				if (str.indexOf('.')==0) {
					str = "0" + str;
				}
				System.out.println(counter + ": " + s + ": " + str.replaceAll("\\.00", ""));

			} else {
				System.out.println(counter + ": " + s + ": " + rvb);

			}*/
			total += rv;
		}

		System.out.println((double)total /100);
		stats.timer("Multiple Dimensional Search");
		System.exit(0);
		in.close();
	}
}