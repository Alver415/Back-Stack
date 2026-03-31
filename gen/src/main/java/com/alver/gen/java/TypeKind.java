package com.alver.gen.java;

public enum TypeKind {
	CLASS("class"),
	INTERFACE("interface"),
	ENUM("enum"),
	RECORD("record"),
	ANNOTATION("annotation");
	
	private final String text;
	
	TypeKind(String text) {
		this.text = text;
	}
	
	public String text() {
		return text;
	}
}
