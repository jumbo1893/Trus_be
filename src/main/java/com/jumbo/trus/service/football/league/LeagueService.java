package com.jumbo.trus.service.football.league;

import com.jumbo.trus.dto.football.LeagueDTO;
import com.jumbo.trus.dto.football.Organization;
import com.jumbo.trus.entity.repository.football.LeagueRepository;
import com.jumbo.trus.mapper.football.LeagueMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueService {

    private final Logger logger = LoggerFactory.getLogger(LeagueService.class);

    private final LeagueRepository leagueRepository;
    private final LeagueProcessor leagueProcessor;
    private final LeagueRetriever leagueRetriever;
    private final LeagueMapper leagueMapper;

    public List<LeagueDTO> getAllLeagues() {
        return leagueRepository.findAll().stream().map(leagueMapper::toDTO).toList();
    }

    public List<LeagueDTO> getAllLeagues(Organization organization) {
        return leagueRepository.findAllByOrganization(organization).stream().map(leagueMapper::toDTO).toList();
    }

    public List<LeagueDTO> getAllLeagues(Organization organization, boolean currentLeague) {
        return currentLeague
                ? leagueRepository.findAllByOrganizationAndCurrentLeague(organization, true).stream().map(leagueMapper::toDTO).toList()
                : getAllLeagues(organization);
    }

    public void updatePkflLeagues() {
        logger.debug("updatuji ligy z PKFL");
        List<LeagueDTO> leaguesFromWeb = leagueRetriever.retrieveLeagues();
        int updatedLeagues = processLeagues(leaguesFromWeb);
        logger.debug("update lig dokončen, počet aktualizovaných lig: {}", updatedLeagues);
        logger.debug("update lig dokončen, celkem se prolezlo lig: {}", leaguesFromWeb.size());
    }

    private int processLeagues(List<LeagueDTO> leaguesFromWeb) {
        if (leaguesFromWeb.isEmpty()) {
            return 0;
        }
        if (leagueProcessor.isNewLeague(leaguesFromWeb.get(0))) {
            return leagueProcessor.saveNewLeaguesToRepository(leaguesFromWeb);
        }

        return leagueProcessor.updateLeagueIfNeeded(leaguesFromWeb);
    }
}
