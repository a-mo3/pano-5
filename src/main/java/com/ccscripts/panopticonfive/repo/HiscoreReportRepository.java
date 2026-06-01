package com.ccscripts.panopticonfive.repo;

import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiscoreReportRepository extends JpaRepository<HiscoreReport, Long> {
}
