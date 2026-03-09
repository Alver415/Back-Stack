package com.alver.demo;

import com.alver.app.PropertiesLoader;

import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		new DemoApplication(PropertiesLoader.load(args));
	}
}
