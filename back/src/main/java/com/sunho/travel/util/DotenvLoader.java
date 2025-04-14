package com.sunho.travel.util;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoader {
    
    public static void loadEnv(){

        Dotenv dotenv = Dotenv.load();
        System.setProperty("EXCHANGE_RATE_AUTHKEY", dotenv.get("EXCHANGE_RATE_AUTHKEY"));
        System.setProperty("JWT_SECRETE_KEY", dotenv.get("JWT_SECRETE_KEY"));

        /*Dotenv dotenv = Dotenv.configure()
           .directory("/path/to/directory")  // 디렉토리만 지정
           .filename("custom.env")           // 파일명도 변경 가능 (선택)
           .load(); 
        */
    }
}
