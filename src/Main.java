import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: [direct / heap]");
			System.exit(1);
		}
		String mode = args[0].toLowerCase();
		boolean useDirect = mode.equals("direct");
		if (!useDirect && !mode.equals("heap")) {
			System.out.println("Usage: [direct / heap]");
			System.exit(1);
		}
		System.out.println("Starting...");
		ArrayList<ByteBuffer> list = new ArrayList<>();
		int x = 1;
		while (true) {
			list.add(randomize(useDirect ? ByteBuffer.allocateDirect(x) : ByteBuffer.allocate(x)));
			if (x != Integer.MAX_VALUE) {
				x <<= 1;
			}
			if (x < 0) {
				x = Integer.MAX_VALUE;
			}
		}
	}

	private static ByteBuffer randomize(ByteBuffer bb) {
		if (bb.hasArray()) {
			nextBytes(bb.array());
		} else {
			while (bb.hasRemaining()) {
				byte[] b = new byte[Math.min(1024, bb.remaining())];
				nextBytes(b);
				bb.put(b);
			}
		}
		return bb;
	}

	private static void nextBytes(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) i;
		}
	}

}
