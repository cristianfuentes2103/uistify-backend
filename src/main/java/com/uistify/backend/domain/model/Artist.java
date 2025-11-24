package com.uistify.backend.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Artist {

	Long id;
	String name;
	String country;
	String portraitUrl;
	Long userId;
}
