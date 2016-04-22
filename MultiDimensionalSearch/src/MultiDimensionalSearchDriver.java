
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
		/**
		 * Test Case
		 */
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-t6.txt", "50966");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-t3.txt", "1660133681.32");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-1.txt", "1450.08");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-2.txt", "4146.32");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-3-1k.txt", "52252.36");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-4-5k.txt", "490409.01");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-5-ck.txt", "173819092858.24");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-t1.txt", "830.28");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-t2.txt", "291968.85");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-t5.txt", "10124");
		map.put("S:\\IMPL_DS\\Algorithms\\MultiDimensionalSearch\\src\\lp4-data\\lp4-t4.txt", "36158262404720.20");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String value = executeMDSD(entry.getKey());
			if (!value.equalsIgnoreCase(entry.getValue())) {
				System.out.println(entry.getKey() + ": E(" + entry.getValue() + ")  Actual=>" + value + " ? "
						+ (value.equalsIgnoreCase(entry.getValue())));
				throw new NullPointerException();
			}
		}
		System.exit(0);
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static String executeMDSD(String args) throws FileNotFoundException {
		Scanner in;
		/*
		 * if (args.length > 0) { in = new Scanner(new File(args)); } else { in
		 * = new Scanner(System.in); }
		 */
		in = new Scanner(new File(args));
		DecimalFormat myFormatter = new DecimalFormat("###.##");
		String s;
		double total = 0;
		description = new long[DLENGTH];
		Statistics stats = new Statistics();
		stats.timer();
		int sameSameCounter=0;
		int insertCounter=0;
		int rangeCounter=0;
		int findMinPriceCounter=0;
		int findMaxPriceCounter=0;
		int hikeCounter=0;
		int counter=0;
		  long startTime, endTime;
			startTime = System.currentTimeMillis();
		
		MultiDimensionalSearch mds = new MultiDimensionalSearch();
		while (in.hasNext()) {
			double rv = 0;
counter++;
if(counter%50000==0)
{
	endTime=System.currentTimeMillis();
	System.out.println(counter+":"+(endTime-startTime));
	startTime=endTime;
}
			s = in.next();
			if (s.charAt(0) == '#') {
				s = in.nextLine();
				continue;
			}
			if (s.equals("Insert")) {
				insertCounter++;
				long id = in.nextLong();
				double price = in.nextDouble();
				long des = in.nextLong();
				int index = 0;
				while (des != 0) {
					description[index++] = des;
					des = in.nextInt();
				}
				description[index] = 0;
				rv += mds.insert(id, price, Arrays.copyOfRange(description, 0, index), index);

			} else if (s.equals("Find")) {
				long id = in.nextLong();
				rv += (mds.find(id));
			} else if (s.equals("Delete")) {
				long id = in.nextLong();
				rv += (mds.delete(id));
			} else if (s.equals("FindMinPrice")) {
				findMinPriceCounter++;
				long des = in.nextLong();
				rv += (mds.findMinPrice(des));
			} else if (s.equals("FindMaxPrice")) {
				findMaxPriceCounter++;
				long des = in.nextLong();
				rv += (mds.findMaxPrice(des));
			} else if (s.equals("FindPriceRange")) {
				long des = in.nextLong();
				double lowPrice = in.nextDouble();
				double highPrice = in.nextDouble();
				rv += mds.findPriceRange(des, lowPrice, highPrice);
			} else if (s.equals("PriceHike")) {
				long minid = in.nextLong();
				hikeCounter++;
				long maxid = in.nextLong();
				double rate = in.nextDouble();
				rv += (mds.priceHike(minid, maxid, rate));
			} else if (s.equals("Range")) {
				rangeCounter++;
				double lowPrice = in.nextDouble();
				double highPrice = in.nextDouble();
				rv += (mds.range(lowPrice, highPrice));
			} else if (s.equals("SameSame")) {
				rv += (mds.samesame());
				sameSameCounter++;
			} else if (s.equals("End")) {
				break;
			} else {
				System.out.println("Houston, we have a problem.\nUnexpected line in input: " + s);
				System.exit(0);
			}

			total += rv;
		}

		System.out.println("sameSameCounter="+sameSameCounter);
		System.out.println("insertCounter="+insertCounter);
		System.out.println("rangeCounter="+rangeCounter); 
		System.out.println("findMinPriceCounter="+findMinPriceCounter); 
		System.out.println("findMaxPriceCounter="+findMaxPriceCounter); 
		System.out.println("hikeCounter="+hikeCounter); 
		System.out.println(myFormatter.format(total));
		stats.timer("Multiple Dimensional Search "+args);
		in.close();
		return myFormatter.format(total);
	}
}