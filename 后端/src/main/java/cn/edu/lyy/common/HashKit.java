package cn.edu.lyy.common;

import java.security.MessageDigest;

public class HashKit {
    private static java.security.SecureRandom random=new java.security.SecureRandom();
    public static String md5(String srcStr){ return hash("MD5",srcStr);}
    public static String shal(String srcStr){ return hash("SHA-1",srcStr);}
    public static String sha256(String srcStr){ return hash("SHA-256",srcStr);}
    public static String sha384(String srcStr){ return hash("SHA-384",srcStr);}
    public static String sha512(String srcStr){ return hash("SHA-512",srcStr);}

    private static String hash(String algorithm,String srcStr){
        try{
            StringBuilder result = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
            for(byte b : bytes){
                String hex = Integer.toHexString(b&0xFF);
                if(hex.length() == 1)
                    result.append("0");
                result.append(hex);
            }
            return result.toString();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }




    }

    private static String toHex(byte[] bytes){
        StringBuilder result = new StringBuilder();
        for(byte b:bytes){
            String hex = Integer.toHexString(b&0xFF);
            if(hex.length()==1)
                result.append("0");
            result.append(hex);
        }
        return result.toString();
    }

    public static String generateSalt(int number0fBytes){
        byte[] salt =new byte[number0fBytes];
        random.nextBytes(salt);
        return toHex(salt);
    }
}

