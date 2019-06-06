package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

//此注解可以配置在配置文件里
@ConfigurationProperties("jwt.config")
public class JwtUtils {

    // 密钥  （配置文件里写为 jwt.config.key)
    private String key;
   //过期时间  (配置文件写为 jwt.config.ttl)
    private  Long ttl;

    //颁发认证信息
    public String createJWT(String id,String userName,String roles){
       return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256,key) //密钥
            .setId(id)
            .setSubject(userName)
            .claim("roles",roles) //  角色
            .setIssuedAt(new Date()) //颁发时间
            .setExpiration(new Date(System.currentTimeMillis()+ ttl)) //设置过期时间一小时 写在配置文件里
            .compact();
    }

    //解析认证信息
    public Claims parserJWT(String jwt){
       return Jwts.parser()
            .setSigningKey(key)  //密钥
            .parseClaimsJws(jwt)  //载荷部分
            .getBody();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
