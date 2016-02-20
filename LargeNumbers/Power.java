
public class Power {

	public static int power(Integer x, int n) {
		System.out.print(n + ":");
		if (n == 0) {
			return 1;
		} else if (n == 1) {
			return x;
		} else {
			int res = power(x * x, n / 2);

			if (n % 2 == 0) {
				return res;
			} else {
				return (res * x);
			}
		}

	}

	public static void main(String[] args) {
		System.out.println(Power.power(2, 4));
		System.out.println(Power.power(2, 21));
	}
}
