package com.alver.gen.model;

import java.time.Instant;

public interface Auditable {
	
	Long createdBy();
	
	Instant createdAt();
	
	Long updatedBy();
	
	Instant updatedAt();
	
	Long deletedBy();
	
	Instant deletedAt();
}
