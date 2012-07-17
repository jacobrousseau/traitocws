package nl.vumc.trait.test;

@SuppressWarnings("javadoc")
public class C1 {

	@SuppressWarnings("serial")
	class Exc1 extends Exception {
		public Exc1() {
			super();
		}

		public Exc1(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public Exc1(String arg0) {
			super(arg0);
		}

		public Exc1(Throwable arg0) {
			super(arg0);
		}
	}

	@SuppressWarnings("serial")
	class Exc2 extends Exception {
		public Exc2() {
			super();
		}

		public Exc2(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public Exc2(String arg0) {
			super(arg0);
		}

		public Exc2(Throwable arg0) {
			super(arg0);
		}
	}

	@SuppressWarnings("serial")
	class Exc3 extends Exception {
		public Exc3() {
			super();
		}

		public Exc3(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public Exc3(String arg0) {
			super(arg0);
		}

		public Exc3(Throwable arg0) {
			super(arg0);
		}
	}

	public void a() throws Exc1 {
		throw new Exc1();
	}

	public void b() throws Exc2 {
		throw new Exc2();
	}

	public void c() throws Exc3 {
		try {
			a();
		} catch (Exception e) {
			throw (Exc3) e;
		}
	}

	public static void main(String args[]) throws Exception {
		C1 c = new C1();
		c.c();
	}

}
