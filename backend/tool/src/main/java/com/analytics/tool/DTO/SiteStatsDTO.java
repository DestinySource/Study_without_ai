package com.analytics.tool.DTO;

public record SiteStatsDTO(
		long totalVisits,
		long totalBounces,
		double bounceRatePct
) {}