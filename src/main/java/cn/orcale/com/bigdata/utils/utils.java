package cn.orcale.com.bigdata.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class utils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("11".compareTo("1"));
		
//		if(Long.parseLong("0")>Long.MIN_VALUE){
//			System.out.println(1111);
//		}else{
//			System.out.println(222);
//		}
		
		
		
//		int num = 1024;
//		
//		System.out.println(bytesToInt(intToBytes(num) , 0) );
//				
//		
//		System.out.println(Bytes2ToString(StringToBytes("wo shi zhongguoren ")));
		
	}

	public static byte[] StringToBytes(String value){
		
		try {
			return value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
    public static String Bytes2ToString(byte[] value){
    	
		try {
			return new String(value,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
//	//为UTF8编码
//	byte[] isoret = srt2.getBytes("ISO-8859-1");
//	//为ISO-8859-1编码
//	其中ISO-8859-1为单字节的编码
//	2.byte[]转string
//	String isoString = new String(bytes,"ISO-8859-1");
	
	
	 public static byte[] intToBytes(int value)   
	 {   
		    byte[] byte_src = new byte[4];  
		    byte_src[3] = (byte) ((value & 0xFF000000)>>24);  
		    byte_src[2] = (byte) ((value & 0x00FF0000)>>16);  
		    byte_src[1] = (byte) ((value & 0x0000FF00)>>8);    
		    byte_src[0] = (byte) ((value & 0x000000FF));          
		    return byte_src;  
	 }  
    
    public static int bytesToInt(byte[] ary, int offset) {  
        int value;    
        value = (int) ((ary[offset]&0xFF)   
                | ((ary[offset+1]<<8) & 0xFF00)  
                | ((ary[offset+2]<<16)& 0xFF0000)   
                | ((ary[offset+3]<<24) & 0xFF000000));  
        return value;  
    }  
	
}
