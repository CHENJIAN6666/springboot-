package com.newlife.s4.util;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 功能：AES128位对称加密类，该类参数需要16位字符串秘钥及待加/解密字符串 <br/>
 * 参考文章： http://blog.csdn.net/coyote1994/article/details/52368921 <br/>
 * http://jueyue.iteye.com/blog/1830792
 * </p>
 * 
 * @ClassName: AES128
 */
public class AES128Utils {
	public static Logger logger = LoggerFactory.getLogger(AES128Utils.class);
//	/**
//	 * 定义一个初始向量，需要与iOS端的统一，使用CBC模式，需要一个向量iv，可增加加密算法的强度
//	 */
//	private static final String IV_STRING = "1234567890abcdef";

	/**
	 * <p class="detail">
	 * 功能：AES128加密方法
	 * </p>
	 * 
	 * @author hxl
	 * @param content  待加密内容
	 * @param key      加密密钥（16位字符串）
	 * 
	 * @param ivString 需要一个向量iv，可增加加密算法的强度
	 * @return 加密后的字符串（结果已经过base64加密）
	 * 
	 * @throws Exception
	 */
	public static String encryptAES(String content, String key, String ivString) throws Exception {
		byte[] byteContent = content.getBytes("UTF-8");
		// 注意，为了能与 iOS 统一
		// 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
		byte[] enCodeFormat = key.getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
		byte[] initParam = ivString.getBytes();
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
		// 指定加密的算法、工作模式和填充方式
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encryptedBytes = cipher.doFinal(byteContent);
		// 同样对加密后数据进行 base64 编码
		return Base64.encodeBase64String(encryptedBytes);
	}

	/**
	 * <p class="detail">
	 * 功能：解密函数
	 * </p>
	 * 
	 * @param content  待解密字符串
	 * @param key      解密秘钥（16位字符串，需要与加密字符串一致）
	 * @param ivString 需要一个向量iv，可增加加密算法的强度
	 * @return
	 * @throws Exception
	 */
	public static String decryptAES(String content, String key, String ivString) throws Exception {
		// 先base64 解码
		byte[] encryptedBytes = Base64.decodeBase64(content);
		byte[] enCodeFormat = key.getBytes();
		SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		byte[] initParam = ivString.getBytes();
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
		// "算法/模式/补码方式"
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		byte[] result = cipher.doFinal(encryptedBytes);
		if (result.length == 0) {
			throw new IllegalBlockSizeException();
		}
		return new String(result, "UTF-8");
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) throws Exception {
		String IV_STRING = "1234567890abcdef";
		String content = "{\"total\":1,\"stationStatusInfo\":{\"operationID\":\"123456789\",\"stationID\":\"111111111111111\",\"connectorStatusInfos\":{\"connectorID\":1,\"equipmentID\":\"10000000000000000000001\",\"status\":4,\"currentA\":0,\"currentB\":0,\"currentC\":0,\"voltageA\":0,\"voltageB\":0,\"voltageC\":0,\"soc\":10,}}}";
		// 必须为16位
		String key = "1234567890abcdef";
		String deKey = "1234567890abcdef";
		String enstring = encryptAES(content, key,IV_STRING);
		String destring = decryptAES(enstring, deKey,IV_STRING);
		System.out.println("原始字符串：" + content);
		System.out.println("加密后字符串：" + enstring);
		System.out.println("长度：" + enstring.length());
		System.out.println("解密后字符串：" + destring);

//		enstring="il7B0BSEjFdzpyKzfOFpvg/Se1CP802RItKYFPfSLRxJ3jf0bV19hvYOEktPAYW2nd7S8MBcyHYyacHKbISq5iTmDzG+ivnR+SZJv3USNTYVMz9rCQVSxd0cLlqsJauko79NnwQJbzDTyLooYoIwz7SqBOH2/xOMirpeEqRJrF/EQjWekJmGk9RtboXePu2rka+Xm51syBPhiXJAq0GfbfaFu9tNqs/e2Vjja/1tE1M0lqvxfXQ6da6HrThsm5id4ClZFIi0acRfrsPLRixS/IQYtksxghvJwbqOsbIsITail9Ayy4tKcogeEZiOO+4Ed264NSKmk7I3wKwJLAFjCFogBx8GE3OBz4pqcAn/ydA=";
//	    destring = decryptAES(enstring, deKey);
//		System.out.println("解密后字符串2：" + destring);

		enstring = "il7B0BSEjFdzpyKzfOFpvg/Se1CP802RItKYFPfSLRxJ3jf0bVl9hvYOEktPAYW2nd7S8MBcyHYyacHKbISq5iTmDzG+ivnR+SZJv3USNTYVMz9rCQVSxd0cLlqsJauko79NnwQJbzDTyLooYoIwz75qBOH2/xOMirpeEqRJrF/EQjWekJmGk9RtboXePu2rka+Xm51syBPhiXJAq0GfbfaFu9tNqs/e2Vjja/ltE1M0lqvxfXQ6da6HrThsm5id4ClZFIi0acRfrsPLRixS/IQYtksxghvJwbqOsbIsITail9Ayy4tKcogeEZiOO+4Ed264NSKmk7l3wKwJLAFjCFogBx8GE3OBz4pqcAn/ydA=";
		destring = decryptAES(enstring, deKey,IV_STRING);
		System.out.println("解密后字符串3：" + destring);
	}
}