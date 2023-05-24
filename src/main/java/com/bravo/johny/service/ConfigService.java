package com.bravo.johny.service;

import com.bravo.johny.dto.LibraryConfig;

public interface ConfigService {

    LibraryConfig getLibraryConfigurations();

    void updateLibraryConfigurations(LibraryConfig config);
}
