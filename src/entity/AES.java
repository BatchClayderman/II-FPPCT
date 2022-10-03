package entity;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AES
{
	private static final String ALGORITHM_NAME = "AES";
	/* �������ӣ��ɸ���������Ҫ�Զ��� */
	private static final String DEFAULT_ENCRYPT_RULE = "AES/CBC/PKCS5Padding";
	private static final String RANDOM_KEY_ALGORITHM = "SHA1PRNG";
	private static final String RANDOM_KEY_ALGORITHM_PROVIDER = "SUN";
	 
    /**
     * AES����
     * @param content �����ܵ����ݣ�Ϊ��ʱΪ�ؿ�
     * @return ���ܺ��base64��ʽ�Ľ���������쳣ʱ����null
     */
    public static String encrypt(String content)
    {
        if (null == content || "" == content)
            return null;
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME);
            SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_KEY_ALGORITHM, RANDOM_KEY_ALGORITHM_PROVIDER);
            secureRandom.setSeed(DEFAULT_ENCRYPT_RULE.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey originalKey = keyGenerator.generateKey();
            SecretKey secretKey = new SecretKeySpec(originalKey.getEncoded(), ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            String result = new String(Base64.getEncoder().encodeToString(encrypted));
            return  result;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
 
 
    /**
          * ����
     * @param encrypted ���ܺ��base64��ʽ������
     * @return ���ܺ��ԭ�ģ������쳣ʱ����null
     */
    public static String decrypt(String encrypted)
    {
        if (null == encrypted || "" == encrypted)
            return null;
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME);
            SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_KEY_ALGORITHM, RANDOM_KEY_ALGORITHM_PROVIDER);
            secureRandom.setSeed(DEFAULT_ENCRYPT_RULE.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey originalKey = keyGenerator.generateKey();
            SecretKey secretKey = new SecretKeySpec(originalKey.getEncoded(), ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(decrypted, "utf-8");
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
            return null;
        }
    }
}