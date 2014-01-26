package Chord_Algorithm;
import java.io.Serializable;
public final class ID implements Comparable<ID>, Serializable {
	

	private static final int HEX = 2;

	private static final int DEC = 1;

	private static final int BIN = 0;
	private static int displayedRepresentation = HEX;
	static {
		String property = System.getProperty(ID.class.getName()
				+ ".displayed.representation");

		if (property != null && property.length() > 0) {
			displayedRepresentation = Integer.parseInt(property);
		}
	}
	private static int numberOfDisplayedBytes = Integer.MAX_VALUE;

	// static initializer for numberOfDisplayedBytes
	static {
		String numberProperty = System.getProperty(ID.class.getName()
				+ ".number.of.displayed.bytes");

		if (numberProperty != null && numberProperty.length() > 0) {
			numberOfDisplayedBytes = Integer.parseInt(numberProperty);
		}
	}
	private final byte[] id;
	
	public ID(byte[] id1) {
		this.id = new byte[id1.length];
		System.arraycopy(id1, 0, this.id, 0, id1.length);
	}
	private transient String stringRepresentation = null;
	public final String toString() {
		if (this.stringRepresentation == null) {
			int rep = ID.displayedRepresentation;
			switch (rep) {
			case 0:
				this.stringRepresentation = this
				.toBinaryString(ID.numberOfDisplayedBytes);
				break; 
			case 1:
				this.stringRepresentation = this
				.toDecimalString(ID.numberOfDisplayedBytes);
				break; 
			default:
				this.stringRepresentation = this
						.toHexString(ID.numberOfDisplayedBytes);
			}

		}
		return this.stringRepresentation;
	}
public final String toHexString(int numberOfBytes) {

		// number of displayed bytes must be in interval [1, this.id.length]
		int displayBytes = Math.max(1, Math.min(numberOfBytes, this.id.length));

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < displayBytes; i++) {

			String block = Integer.toHexString(this.id[i] & 0xff).toUpperCase();

			// add leading zero to block, if necessary
			if (block.length() < 2) {
				block = "0" + block;
			}

			result.append(block + " ");
		}
		return result.toString();
	}
	public final String toHexString() {
		return this.toHexString(this.id.length);
	}
public final String toDecimalString(int numberOfBytes) {

		// number of displayed bytes must be in interval [1, this.id.length]
		int displayBytes = Math.max(1, Math.min(numberOfBytes, this.id.length));

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < displayBytes; i++) {

			String block = Integer.toString(this.id[i] & 0xff);

			result.append(block + " ");
		}
		return result.toString();
	}
	public final String toDecimalString() {
		return this.toDecimalString(this.id.length);
	}
public final String toBinaryString(int numberOfBytes) {

		// number of displayed bytes must be in interval [1, this.id.length]
		int displayBytes = Math.max(1, Math.min(numberOfBytes, this.id.length));

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < displayBytes; i++) {

			String block = Integer.toBinaryString(this.id[i] & 0xff);

			// add leading zero to block, if necessary
			while (block.length() < 8) {
				block = "0" + block;
			}

			result.append(block + " ");
		}
		return result.toString();
	}
	public final String toBinaryString() {
		return this.toBinaryString(this.id.length);
	}
public final int getLength() {
		return this.id.length * 8;
	}

public final ID addPowerOfTwo(int powerOfTwo) {

		if (powerOfTwo < 0 || powerOfTwo >= (this.id.length * 8)) {
			throw new IllegalArgumentException(
					"The power of two is out of range! It must be in the interval "
							+ "[0, length-1]");
		}

		// copy ID
		byte[] copy = new byte[this.id.length];
		System.arraycopy(this.id, 0, copy, 0, this.id.length);

		// determine index of byte and the value to be added
		int indexOfByte = this.id.length - 1 - (powerOfTwo / 8);
		byte[] toAdd = { 1, 2, 4, 8, 16, 32, 64, -128 };
		byte valueToAdd = toAdd[powerOfTwo % 8];
		byte oldValue; 

		do {
			// add value
			oldValue = copy[indexOfByte];
			copy[indexOfByte] += valueToAdd;

			// reset value to 1 for possible overflow situation
			valueToAdd = 1;
		}
		// check for overflow - occurs if old value had a leading one, i.e. it
		// was negative, and new value has a leading zero, i.e. it is zero or
		// positive; indexOfByte >= 0 prevents running out of the array to the
		// left in case of going over the maximum of the ID space
		while (oldValue < 0 && copy[indexOfByte] >= 0 && indexOfByte-- > 0);

		return new ID(copy);
	}
public final boolean equals(Object equalsTo) {

		// check if given object has correct type
		if (equalsTo == null || !(equalsTo instanceof ID)) {
			return false;
		}

		// check if both byte arrays are equal by using the compareTo method
		return (this.compareTo((ID) equalsTo) == 0);

	}
	public final int compareTo(ID otherKey) throws ClassCastException {

		if (this.getLength() != otherKey.getLength()) {
			throw new ClassCastException(
					"Only ID objects with same length can be "
							+ "compared! This ID is " + this.id.length
							+ " bits long while the other ID is "
							+ otherKey.getLength() + " bits long.");
		}

		// compare value byte by byte
		byte[] otherBytes = new byte[this.id.length];
		System.arraycopy(otherKey.id, 0, otherBytes, 0, this.id.length);

		for (int i = 0; i < this.id.length; i++) {
			if ((byte) (this.id[i] - 128) < (byte) (otherBytes[i] - 128)) {
				return -1; // this ID is smaller
			} else if ((byte) (this.id[i] - 128) > (byte) (otherBytes[i] - 128)) {
				return 1; // this ID is greater
			}
		}
		return 0;

	}
public final int hashCode() {
		int result = 19;
		for (int i = 0; i < this.id.length; i++) {
			result = 13 * result + this.id[i];
		}
		return result;
	}
	
	public final boolean isInInterval(ID fromID, ID toID) {

		// both interval bounds are equal -> calculate out of equals
		if (fromID.equals(toID)) {
			// every ID is contained in the interval except of the two bounds
			return (!this.equals(fromID));
		}

		// interval does not cross zero -> compare with both bounds
		if (fromID.compareTo(toID) < 0) {
			return (this.compareTo(fromID) > 0 && this.compareTo(toID) < 0);
		}

		// interval crosses zero -> split interval at zero
		// calculate min and max IDs
		byte[] minIDBytes = new byte[this.id.length];
		ID minID = new ID(minIDBytes);
		byte[] maxIDBytes = new byte[this.id.length];
		for (int i = 0; i < maxIDBytes.length; i++) {
			maxIDBytes[i] = -1;
		}
		ID maxID = new ID(maxIDBytes);
		// check both splitted intervals
		// first interval: (fromID, maxID]
		return ((!fromID.equals(maxID) && this.compareTo(fromID) > 0 && this
				.compareTo(maxID) <= 0) ||
		// second interval: [minID, toID)
		(!minID.equals(toID) && this.compareTo(minID) >= 0 && this
				.compareTo(toID) < 0));
	}

}