package test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

public class JjwtDemo {

    @Test
    public void fun(){

        String compact = Jwts.builder() // 获得Jwt 的构建器
                .signWith(SignatureAlgorithm.HS256, "aaaaa")//指定编码方式 指定密钥
                .setId("888")//指定载荷部分中键值对信息
                .setSubject("tom")  //指定载荷部分中键值对信息
                .setIssuedAt(new Date()) //设置认证生成时间
                .compact();//生成JWT 密文认证信息

        System.out.println(compact);

        // eyJhbGciOiJIUzI1NiJ9.    //指定编码方式 指定密钥部分
        // eyJqdGkiOiI4ODgiLCJzdWIiOiJ0b20iLCJpYXQiOjE1NTY0MTQzOTd9.    //载荷部分
        // fNai5aWvht9Y1vTluGXs2D7fg_dVL1KeXyeWlNyj0tA  //签名部分
    }

    @Test
    //提取认证信息
    public void fun2(){
        String jwt ="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJ0b20iLCJpYXQiOjE1NTY0MTQzOTd9.fNai5aWvht9Y1vTluGXs2D7fg_dVL1KeXyeWlNyj0tA";
        Claims body = Jwts.parser() //获得JWT 解析器
                .setSigningKey("aaaaa") //指定密钥
                .parseClaimsJws(jwt)//指定解析的 JWT
                .getBody();  //获取载荷部分

        String id = body.getId();
        String subject = body.getSubject();
        Date issuedAt = body.getIssuedAt();

        System.out.println(id);
        System.out.println(subject);
        System.out.println(issuedAt);
    }

    @Test
    public void fun3(){
        String compact = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "bbbb")
                .setId("999")
                .setSubject("tengfei")
                .setIssuedAt(new Date())
                .claim("roles", "admin")//自定义载荷信息
                .claim("hehe", "heihie")//自定义载荷信息
                .compact();
        System.out.println(compact);
    }
    @Test
    //解析
    public void fun4(){
     String jwt ="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5OTkiLCJzdWIiOiJ0ZW5nZmVpIiwiaWF0IjoxNTU2NDE2MzczLCJyb2xlcyI6ImFkbWluIiwiaGVoZSI6ImhlaWhpZSJ9.AQCi3SGJPtd6-XaNs2Zs02pXfz2ip2JZ9DuEPEy6zgY";
        Claims body = Jwts.parser()
                .setSigningKey("bbbb")
                .parseClaimsJws(jwt)
                .getBody();

        String id = body.getId();
        String subject = body.getSubject();
        Date issuedAt = body.getIssuedAt();
        String roles = (String) body.get("roles");
        String hehe = (String) body.get("hehe");

        System.out.println(id);
        System.out.println(subject);
        System.out.println(issuedAt);
        System.out.println(roles);
        System.out.println(hehe);
    }

    @Test
    //设置过期时间
    public void fun5(){
        String compact = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "cccc")
                .setId("000")
                .setSubject("TF")
                .setIssuedAt(new Date())
                .claim("hehe", "haha")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 50)) //50 秒后过期
                .compact();
        System.out.println(compact);
    }

    @Test
    //解析
    public void fun6(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMDAiLCJzdWIiOiJURiIsImlhdCI6MTU1NjQxODE1MiwiaGVoZSI6ImhhaGEiLCJleHAiOjE1NTY0MTgyMDJ9.GTT3p0ka_vpwV0QKePzWHjHTPLXE6E3tFxgmueJvV2k";
        Claims body = Jwts.parser()
                .setSigningKey("cccc")
                .parseClaimsJws(jwt)
                .getBody();

        String id = body.getId();
        String subject = body.getSubject();
        Date issuedAt = body.getIssuedAt();
        String hehe = (String) body.get("hehe");
        Date expiration = body.getExpiration();//获取过期时间

        System.out.println(id);
        System.out.println(subject);
        System.out.println("注册时间："+issuedAt);
        System.out.println(hehe);
        System.out.println("过期时间："+expiration);
    }
}
