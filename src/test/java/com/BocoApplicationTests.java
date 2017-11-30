package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BocoApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println(this.getClass().getClassLoader());
	}

	public static void main(String[] args) throws IOException {
		File file=new File("C:\\Users\\jack\\Desktop\\result.sql");
		FileInputStream in=new FileInputStream(file);

		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));

		StringBuilder sb=new StringBuilder();
		String line=null;
		while ((line = bufferedReader.readLine()) != null) {

				sb.append(line);
			}
		  System.out.println(sb);
			in.close();



	}
}