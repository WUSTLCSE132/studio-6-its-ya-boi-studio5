package studio6;

import jssc.*;

public class SerialComm {

	SerialPort port;

	private boolean debug;  // Indicator of "debugging mode"
	
	// This function can be called to enable or disable "debugging mode"
	void setDebug(boolean mode) {
		debug = mode;
	}	
	

	// Constructor for the SerialComm class
	public SerialComm(String name) throws SerialPortException {
		port = new SerialPort(name);		
		port.openPort();
		port.setParams(SerialPort.BAUDRATE_9600,
			SerialPort.DATABITS_8,
			SerialPort.STOPBITS_1,
			SerialPort.PARITY_NONE);
		
		debug = false; // Default is to NOT be in debug mode
	}
		
	// TODO: Add writeByte() method from Studio 5
	public void writeByte(byte b) throws SerialPortException {
		try {
			port.writeByte(b);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
		if(debug) {
			String s = String.format("%02X", b);
			port.writeString(s);
		}
	}

	// TODO: Add available() method
	public boolean available() {
		try {
			return port.getInputBufferBytesCount() > 0;
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	// TODO: Add readByte() method	
	public byte readByte() throws SerialPortException {
		byte b[] = port.readBytes(1);
		return b[0];
	}
	// TODO: Add a main() method
	public static void main(String[] args) throws SerialPortException {
		SerialComm p = new SerialComm("/dev/cu.usbserial-DN02B9TQ");
		p.setDebug(true);
		
		while(true) {
			if(p.available()) {
				char c = (char)p.readByte();
				byte bt = p.readByte();
				if(p.debug) {
					System.out.println(c);
					System.out.println(String.format("%02X", bt));
				}
				else System.out.println(c);
			}
		}
	}
}
