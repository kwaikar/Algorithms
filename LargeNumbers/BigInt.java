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
	public   boolean isPositive = true;

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
				while (input.charAt(0) == '0') {
					input = input.replaceFirst("0", "");
				}
				if(input.length()>0){
				coefficients = new ArrayList<Integer>(input.length());
				for (int i = 0; i < input.length(); i++) {
					coefficients.add(i, Integer.parseInt(input.charAt(input.length() - 1 - i) + ""));
				}
				}
				else
				{
					isPositive=true;
					coefficients.add(0,0);
					
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
	 * @param num1 - Number 1 
	 * @param num2 - Number 2
	 * @return
	 */
	public static BigInt add(  BigInt num1, BigInt num2) {
		BigInt output = new BigInt();
		
		if(!num2.isPositive&& !num1.isPositive){
			output.isPositive=false;
		}
		else if(!num2.isPositive)
		{
			num2.isPositive=true;
			output= subtract(num1, num2);
			num2.isPositive=false;
			return output;
		}
		else if (!num1.isPositive)
		{
			num1.isPositive=true;
			output= subtract(num1, num2);
			num1.isPositive=false;
			
		}

		output.coefficients = new ArrayList<Integer>((num1.coefficients.size() > num2.coefficients.size()
				? num1.coefficients.size()  : num2.coefficients.size()) + 1);
		/**
		 * i - Counter for looping through coefficient arrays
		 * carry - Boolean flag for checking whether carry is set or not.
		 * unfinishedList - Reference to the Larger list.
		 */
		int i = 0;
		boolean carry = false;
		
		while (i < num1.coefficients.size() && i < num2.coefficients.size()) {
			/**
			 * Add both common coefficients
			 */
			int temp = num1.coefficients.get(i) + num2.coefficients.get(i) + (carry?1:0);
			if( temp >= BASE )
			{
				output.coefficients.add(i++,  temp % BASE);
				carry=true;
			}
			else
			{
				output.coefficients.add(i++,  temp);
				carry=false;
			}
		}
		
		List<Integer> unfinishedList = (i != num1.coefficients.size()) ? num1.coefficients : num2.coefficients;
		
		while (i != unfinishedList.size()) {
			/**
			 * Loop over the larger number and add the coefficients after considering the carry to the output.
			 */
			int temp = unfinishedList.get(i) + (carry?1:0);
			if( temp >= BASE )
			{
				output.coefficients.add(i++,  temp % BASE);
				carry=true;
			}
			else
			{
				output.coefficients.add(i++,  temp);
				carry=false;
			}
		}
		if(carry)
		{
			output.coefficients.add(i++,  1);
		}
		return output;
	}

	/**
	 * This method returns whether both numbers are equal or not.
	 */
	@Override
	public boolean equals(Object obj) {
		BigInt number =(BigInt) obj;
		return this.toString().equalsIgnoreCase(number.toString());
	}
	
	/**
	 * Swaps num1 and num2 References
	 * @param num1
	 * @param num2
	 */
	private static void swap(BigInt num1,BigInt num2)
	{
	BigInt	output=num2;
		num2=num1;
		num1=output;
		output=null;
	}
	/**
	 * This Static method accepts two numbers and subtracts right from left. 
	 * @param num1 - Number 1 
	 * @param num2 - Number 2
	 * @return
	 */
	public static BigInt subtract(  BigInt num1, BigInt num2) {
		BigInt output = new BigInt();
		boolean areBothNegative=false;
		if(num1.isPositive && !num2.isPositive)
		{
			num2.isPositive=true;
			output= add(num1,num2);
			num2.isPositive=false;
			return output;
		}
		else if(num2.isPositive && !num1.isPositive)
		{
			num1.isPositive=true;
			output = add(num1,num2);
			output.isPositive=false;
			num1.isPositive=false;
			return output;
		}
		else if(!num2.isPositive)
		{
			// num1 is negative too
			areBothNegative=true;
		}
		
		if( num1.coefficients.size()==num2.coefficients.size())
		{
			int counter=num2.coefficients.size()-1;
			/**
			 * num1 should contain bigger number and num2 should contain smaller number
			 */
			System.out.println(num1+":"+num2);
			while(counter>=0 &&(num2.coefficients.get(counter)==num2.coefficients.get(counter)|| num1.coefficients.get(counter)>num2.coefficients.get(counter)))
			{
				counter--;
			}
			if (counter!=0)
			{
				swap(num1,num2);
				output.isPositive=!areBothNegative?false:true;
			}
		}
		else if(num2.coefficients.size()>num1.coefficients.size())
		{
			swap(num1, num2);
			output.isPositive=!areBothNegative?false:true;
		}

		output.coefficients = new ArrayList<Integer>((num1.coefficients.size() > num2.coefficients.size()
				? num1.coefficients.size()  : num2.coefficients.size())  );
		/**
		 * i - Counter for looping through coefficient arrays
		 * carry - Boolean flag for checking whether carry is set or not.
		 * unfinishedList - Reference to the Larger list.
		 */
		int i = 0;
		boolean loan = false;
		
		while (i < num1.coefficients.size() && i < num2.coefficients.size()) {
			/**
			 * Add both common coefficients
			 */
			int valueToBeSubtracted = num2.coefficients.get(i) + (loan?1:0);
			if(valueToBeSubtracted >num1.coefficients.get(i))
			{
				loan=true;
				output.coefficients.add(i,  ((num1.coefficients.get(i)+BASE)-num2.coefficients.get(i)));
			}
			else
			{
				output.coefficients.add(i,  ((num1.coefficients.get(i))-num2.coefficients.get(i)));
				loan=false;
			}
			i++;
		}
		
		
		while (i != num1.coefficients.size()) {
			/**
			 * Loop over the larger number and add the coefficients after considering the carry to the output.
			 */
			int temp = num1.coefficients.get(i) - (loan?1:0);
			if( temp <0)
			{
				output.coefficients.add(i++,  temp+BASE );
				loan=true;
			}
			else
			{
				output.coefficients.add(i++,  temp);
				loan=false;
			}
		}
		int counter=output.coefficients.size()-1;
		while(output.coefficients.get(counter)==0 && counter!=0)
		{
			output.coefficients.remove(counter--);
		}
		if(output.coefficients.size()==1 && output.coefficients.get(0)==0)
		{
			output.isPositive=true;	
		}
		
		if(!output.isPositive)
		{
			swap(num1,num2);
		}
		return output;
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
		return this.isPositive?sb.reverse().toString():"-"+sb.reverse().toString() ;
	}

	public static void main(String[] args) {
		BigInt xyz = new BigInt("004268");
		System.out.println("Test case 1 status:" + xyz.toString().equals("4268"));
		System.out.println("Test case 2 status:" + new BigInt(4268L).toString().equals("4268"));
		System.out.println("Test case 2 status:" + new BigInt(new Long(4268)).toString().equals("4268"));
		System.out.println((BigInt.add(new BigInt(999L), new BigInt(1L))).toString().equals("1000"));
		System.out.println((BigInt.add(new BigInt(99L), new BigInt(99L))).toString().equals("198"));
		System.out.println((BigInt.add(new BigInt(-99L), new BigInt(-99L))).toString().equals("-198"));
		System.out.println(BigInt.subtract(new BigInt(99L), new BigInt(99L)));
		System.out.println((BigInt.subtract(new BigInt(99L), new BigInt(99L))).toString().equals("0"));
		System.out.println((BigInt.subtract(new BigInt(99L), new BigInt(-99L))).toString().equals("198"));
		System.out.println((BigInt.subtract(new BigInt(-99L), new BigInt(99L))).toString().equals("-198"));
		System.out.println((BigInt.subtract(new BigInt(-99L), new BigInt(-99L))).toString().equals("0"));
	}
}