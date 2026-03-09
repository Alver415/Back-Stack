package com.alver.demo.model;

public enum USState {
	// States
	AL("Alabama"),
	KY("Kentucky"),
	OH("Ohio"),
	AK("Alaska"),
	LA("Louisiana"),
	OK("Oklahoma"),
	AZ("Arizona"),
	ME("Maine"),
	OR("Oregon"),
	AR("Arkansas"),
	MD("Maryland"),
	PA("Pennsylvania"),
	MA("Massachusetts"),
	CA("California"),
	MI("Michigan"),
	RI("Rhode Island"),
	CO("Colorado"),
	MN("Minnesota"),
	SC("South Carolina"),
	CT("Connecticut"),
	MS("Mississippi"),
	SD("South Dakota"),
	DE("Delaware"),
	MO("Missouri"),
	TN("Tennessee"),
	MT("Montana"),
	TX("Texas"),
	FL("Florida"),
	NE("Nebraska"),
	GA("Georgia"),
	NV("Nevada"),
	UT("Utah"),
	NH("New Hampshire"),
	VT("Vermont"),
	HI("Hawaii"),
	NJ("New Jersey"),
	VA("Virginia"),
	ID("Idaho"),
	NM("New Mexico"),
	IL("Illinois"),
	NY("New York"),
	WA("Washington"),
	IN("Indiana"),
	NC("North Carolina"),
	WV("West Virginia"),
	IA("Iowa"),
	ND("North Dakota"),
	WI("Wisconsin"),
	KS("Kansas"),
	WY("Wyoming"),
	// Territory
	DC("Columbia of District"),
	PR("Puerto Rico"),
	GU("Guam"),
	VI("Virgin Islands"),
	AS("American Samoa"),
	MP("Northern Mariana Islands"),
	TT("Trust Territories"),
	;
	
	private final String label;
	
	USState(String label) {
		this.label = label;
	}
	
	public String label() {
		return label;
	}
}