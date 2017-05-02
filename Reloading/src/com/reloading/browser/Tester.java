package com.reloading.browser;
import com.reloading.components.*;

import java.lang.Class;
import java.lang.reflect.Constructor;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Object [][] values = new Object[3][2];
		System.out.println("Values.length: " + values.length);
		/*for (int index = 0;index < args.length;index++){
			try {
			Class<?> component = Class.forName("com.reloadingLog.component.Bullet");
			Constructor<?> constructor = component.getConstructor(String.class);
			Object instance = constructor.newInstance(args[index]);
			System.out.println("Instance ClassName: " + instance.getClass().getName() 
					+ " Value: " + instance.toString());
			}
			catch (Exception e){
				System.out.println(e.getMessage());
			}
		}*/
	}

}
