package com.coderead.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tommy
 * @title: Test
 * @projectName coderead-mybatis
 * @description: TODO
 * @date 2020/6/33:24 PM
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("/Users/tommy/git/coderead-mybatis/test.txt"));
        List<String> collect = lines.collect(Collectors.toList());
        Collections.shuffle(collect);
        char group = 'l';
        int index = 1;
        for (String s : collect) {
            System.out.println(group + "" + index + " " + s);
            index++;
            if (index == 11) {
                group++;
                index = 1;
            }
        }
    }
}
