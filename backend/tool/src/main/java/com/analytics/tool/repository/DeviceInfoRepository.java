package com.analytics.tool.repository;


import com.analytics.tool.model.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {
    @Query("SELECT u.browser, COUNT(u) FROM DeviceInfo u GROUP BY u.browser")
    List<Object[]> getBrowserCounts();
}
