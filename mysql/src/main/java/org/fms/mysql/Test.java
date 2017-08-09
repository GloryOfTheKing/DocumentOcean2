package org.fms.mysql;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Scanner;

/**
 * Created by lion on 2017/8/3.
 */
public class Test {
    public static void main(String args[]){
/*        Scanner in = new Scanner(System.in);
        String s  = in.next();
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        System.out.print(bpe.encode(s));*/
        PathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(pathMatcher.match("user","/user/test"));
    }
}
