package com.example.demoandroid.data.persistence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.example.demoandroid.utils.Constants;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PreferencesManager {

    public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final String KEY_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    public static final String SECRET_KEY_HASH_TRANSFORMATION = "SHA-256";
    public static final String CHARSET = "UTF-8";
    public static final String SEC_PREFERENCES = "secPrefStore";
    public static final String CURRENT_KEY_MODE = "curKeyMode" ;

    public static final String TOKEN_SESSION = "token_session";
    private Cipher writer;
    private Cipher reader;
    private Cipher keyWriter;
    private SharedPreferences preferences;

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private static PreferencesManager instance;

    /**
     * This will initialize an instance of the SecurePreferences class
     * @param context your current context.
     * @param preferenceName name of preferences file (preferenceName.xml)
     */
    @SuppressLint({"GetInstance", "CommitPrefEdits"})
    public PreferencesManager(Context context, String preferenceName) throws SecurePreferencesException {
        if (context != null && preferenceName != null && !preferenceName.isEmpty()) {
            try {
                this.writer = Cipher.getInstance(TRANSFORMATION);
                this.reader = Cipher.getInstance(TRANSFORMATION);
                this.keyWriter = Cipher.getInstance(KEY_TRANSFORMATION);
                initCiphers();
                this.preferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            } catch (GeneralSecurityException | UnsupportedEncodingException e) {
                throw new SecurePreferencesException(e);
            }
        }

    }


    public static  PreferencesManager getInstance(Context context , String preferenceName) {
        if (instance == null) {
            instance = new PreferencesManager(context , preferenceName);
        }
        return instance;
    }

    public static class SecurePreferencesException extends RuntimeException {

        SecurePreferencesException(Throwable e) {
            super(e);
        }
    }

    private void initCiphers() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException {
        IvParameterSpec ivSpec = getIv();
        SecretKeySpec secretKey = getSecretKey();

        writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        keyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    private IvParameterSpec getIv() {
        byte[] iv = new byte[writer.getBlockSize()];
        System.arraycopy("fldsjfodasjifudslfjdsaofshaufihadsf".getBytes(), 0, iv, 0, writer.getBlockSize());
        return new IvParameterSpec(iv);
    }

    private SecretKeySpec getSecretKey() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] keyBytes = createKeyBytes();
        return new SecretKeySpec(keyBytes, TRANSFORMATION);
    }

    private byte[] createKeyBytes() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SECRET_KEY_HASH_TRANSFORMATION);
        md.reset();
        return md.digest(Constants.SEC_PREFS_KEY.getBytes(CHARSET));
    }

    public void put(String key, String value) {
        if (value == null) {
            preferences.edit().remove(toKey(key)).apply();
        }
        else {
            putValue(toKey(key), value);
        }
    }

    public void put(String key, Integer value) {
        if (value == null) {
            preferences.edit().remove(toKey(key)).apply();
        }
        else {
            putValue(toKey(key), String.valueOf(value));
        }
    }

    public void put(String key, boolean value){
        put(key, value? TRUE : FALSE);
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        put(key, json);
    }

    public boolean containsKey(String key) {
        return preferences.contains(toKey(key));
    }

    public void removeValue(String key) {
        preferences.edit().remove(toKey(key)).apply();
    }

    public String getString(String key) throws SecurePreferencesException {
        if (preferences.contains(toKey(key))) {
            String securedEncodedValue = preferences.getString(toKey(key), "");
            return decrypt(securedEncodedValue);
        }
        return null;
    }

    public int getInt(String key) {
        if (preferences.contains(toKey(key))) {
            String securedEncodedValue = preferences.getString(toKey(key), "");
            if (!decrypt(securedEncodedValue).isEmpty()) return Integer.parseInt(decrypt(securedEncodedValue));
            else return 0;
        }
        return 0;
    }

    public boolean getBoolean(String key, boolean bDefault) throws SecurePreferencesException{
        String value = getString(key);
        if(value!=null){
            return value.equalsIgnoreCase(TRUE);
        }else{
            return bDefault;
        }
    }

    public String getTokenSession(){
        return getString(TOKEN_SESSION);
    }


    public void clear() {
        preferences.edit().clear().apply();
    }

    private String toKey(String key) {
        return encrypt(key, keyWriter);
    }

    private void putValue(String key, String value) throws SecurePreferencesException {
        String secureValueEncoded = encrypt(value, writer);

        preferences.edit().putString(key, secureValueEncoded).apply();
    }

    private String encrypt(String value, Cipher writer) throws SecurePreferencesException {
        byte[] secureValue;
        try {
            secureValue = convert(writer, value.getBytes(CHARSET));
        }
        catch (UnsupportedEncodingException e) {
            throw new SecurePreferencesException(e);
        }
        return Base64.encodeToString(secureValue, Base64.NO_WRAP);
    }

    private String decrypt(String securedEncodedValue) {
        byte[] securedValue = Base64.decode(securedEncodedValue, Base64.NO_WRAP);
        byte[] value = convert(reader, securedValue);
        try {
            return new String(value, CHARSET);
        }
        catch (UnsupportedEncodingException e) {
            throw new SecurePreferencesException(e);
        }
    }

    private static byte[] convert(Cipher cipher, byte[] bs) throws SecurePreferencesException {
        try {
            return cipher.doFinal(bs);
        }
        catch (Exception e) {
            throw new SecurePreferencesException(e);
        }
    }
}
