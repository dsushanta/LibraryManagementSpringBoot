package com.bravo.johny.service.Implementations;

import com.bravo.johny.dto.LibraryConfig;
import com.bravo.johny.entity.ConfigEntity;
import com.bravo.johny.repository.ConfigRepository;
import com.bravo.johny.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConfigServiceImplementation implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public LibraryConfig getLibraryConfigurations() {

        ConfigEntity configEntity = configRepository.findAll().get(0);

        return prepareConfigDTOFromConfigEntity(configEntity);
    }

    @Override
    public void updateLibraryConfigurations(LibraryConfig config) {

        ConfigEntity configEntity = prepareConfigEntityFromConfigDTO(config);
        configRepository.save(configEntity);
    }

    // ##################### PRIVATE METHODS ######################

    private ConfigEntity prepareConfigEntityFromConfigDTO(LibraryConfig config) {

        return new ConfigEntity(
                config.getNO_OF_DAYS_A_USER_CAN_KEEP_A_BOOK(),
                config.getFINE_PER_DAY(),
                config.getNUMBER_OF_BOOKS_PER_USER()
        );
    }

    private LibraryConfig prepareConfigDTOFromConfigEntity(ConfigEntity configEntity) {

        LibraryConfig config = new LibraryConfig();
        config.setNO_OF_DAYS_A_USER_CAN_KEEP_A_BOOK(configEntity.getNoOfDaysAUserCanKeepABook());
        config.setFINE_PER_DAY(configEntity.getFinePerDay());
        config.setNUMBER_OF_BOOKS_PER_USER(configEntity.getNoOfBooksPerUser());

        return config;
    }
}
