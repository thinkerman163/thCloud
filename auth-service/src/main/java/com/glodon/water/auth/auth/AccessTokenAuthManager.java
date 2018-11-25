package com.glodon.water.auth.auth;

import java.security.Key;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.glodon.water.common.common.enumpo.TokenTypeEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@ConfigurationProperties(prefix = "system.auth")
@Component
public class AccessTokenAuthManager {
	private String jwtKey;
	private long validTime;
	/**
	 * 加密key，jws使用时会复制一份，不会发生并发问题
	 */
	private Key key;

	@PostConstruct
	public void init() {
		key = generateKey(jwtKey);
	}

	/**
	 * 生成token
	 * 
	 * @param target
	 *            需要保存到token的对象信息，一般为User对象或map对象
	 * @return token
	 * @throws ParseException
	 */
	public String generateToken(Object target) {
		return Jwts.builder().setSubject(target.toString()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + validTime)).signWith(SignatureAlgorithm.HS512, key).compact();
	}

	public String getJwtKey() {
		return jwtKey;
	}

	public void setJwtKey(String jwtKey) {
		this.jwtKey = jwtKey;
	}

	public long getValidTime() {
		return validTime;
	}

	public void setValidTime(long validTime) {
		this.validTime = validTime;
	}

	/**
	 * 获取token上记录的信息
	 * 
	 * @param token
	 * @return token上记录的信息
	 */
	public String parseToken(String token) {
		Claims claim = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		String innertoken=TokenTypeEnum.REST.getName()+"|"+claim.getSubject()+"|"+System.currentTimeMillis();			
        byte[] bytes = innertoken.getBytes();  
        innertoken=Base64.getEncoder().encodeToString(bytes);      
		return innertoken;
	}

	/**
	 * 根据字符串key获取Key对象
	 * 
	 * @param key
	 * @return
	 */
	public Key generateKey(String key) {
		Key signingKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS512.getJcaName());
		return signingKey;
	}
}
