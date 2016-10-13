package com.fushionbaby.common.util;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class RandomNumUtil {
	
	public static String NUM = "1";// 纯数字

	public static String CHAR = "2";// 纯字符

	public static String NUM_CHAR = "3";// 字符和数字组合
	
	
	public static Integer REGISTER_USER_LENGTH = 4;// 注册-短信长度
	
	public static Integer UPDATE_PASSWARD_LENGTH = 4;// 修改密码-短信长度
	
	public static Random random = new Random();

	private RandomNumUtil() {
	}

	/**
	 * 获取随机数
	 * 
	 * @param type
	 *            随机数类型
	 * @param length
	 *            随机数长度
	 * @return
	 */
	public static String getRandom(String type, int length) {
		String str;

		if (NUM.equals(type))
			str = getNumber(length);
		else if (CHAR.equals(type))
			str = getCharacter(length);
		else
			str = getCharacterAndNumber(length);

		return str;
	}

	/**
	 * 获取纯数字的随机验证码
	 * 
	 * @param length
	 * @return
	 */
	public static String getNumber(int length) {
		String sRand = "";
		for (int i = 0; i < length; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}

		return sRand;
	}

	private static String getCharacter(int length) {
		String sRand = "";
		for (int i = 0; i < length; i++) {
			int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
			sRand += (char) (choice + random.nextInt(26));
		}

		return sRand;
	}

	/**
	 * 获取字符和数字组合而成的随机验证码
	 * 
	 * @param length
	 * @return
	 */
	public static String getCharacterAndNumber(int length) {
		String sRand = "";

		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {// 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				sRand += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) {// 数字
				sRand += String.valueOf(random.nextInt(10));
			}
		}

		return sRand;
	}
	
	/**
	 * 生成随机文件名
     * 以UUID的策略生成一个长度为32的字符串，在同一时空中具有唯一性。  
     * @return   
     */  
    public static String getUUIDString() {   
        String id = UUID.randomUUID().toString();   
        id = id.replace("-", "");   
        return id;   
    }
    
    
    public static int getNextInt(int number) {   
        return random.nextInt(number);
    }
    
    public static void main(String args[])
    {
    	System.out.println(RandomNumUtil.getNextInt(3));
    	System.out.println(RandomNumUtil.getRandom(RandomNumUtil.NUM, 4));
    	System.out.println(RandomNumUtil.getCharacterAndNumber(18));
    }

    
    /** 
	 * 随机指定范围内N个不重复的数 
	 * 利用HashSet的特征，只能存放不同的值 
	 * @param min 指定范围最小值 
	 * @param max 指定范围最大值 
	 * @param n 随机数个数 
	 * @param HashSet<Integer> set 随机数结果集 
	 */  
	 public static void randomSet(int min, int max, int n, HashSet<Integer> set) {  
		 if (n > (max - min + 1) || max < min) {  
			 return;  
	     }  
	     for (int i = 0; i < n; i++) {  
	    	 // 调用Math.random()方法  
	         int num = (int) (Math.random() * (max - min)) + min;  
	         set.add(num);// 将不同的数存入HashSet中  
	     }  
	     int setSize = set.size();  
	     // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小  
	     if (setSize < n) {  
	    	 randomSet(min, max, n - setSize, set);// 递归  
	     }
	 }
}
