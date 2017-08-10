package com.example.feild.encryption.domain.em;

public enum Sex implements IntEnumInter<Sex> {

	MALE(1, "男"), 
	FEMALE(2, "女")
	;

	private int value;
	private String desc;

	private Sex(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	@Override
	public int intValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.desc;
	}
}
