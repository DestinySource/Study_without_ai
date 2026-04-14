package com.analytics.tool.DTO;

public record VisitRequestDTO(
		String page,
		String referrer,
		String source,
		Integer timeOnPage,
		Boolean isBounce
) {}