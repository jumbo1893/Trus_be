package com.jumbo.trus.entity.repository.football;

import com.jumbo.trus.entity.football.TableTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableTeamRepository extends JpaRepository<TableTeamEntity, Long> {

    TableTeamEntity findByTeamIdAndLeagueId(Long teamId, Long leagueId);
}
