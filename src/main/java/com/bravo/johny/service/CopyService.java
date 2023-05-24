package com.bravo.johny.service;

import com.bravo.johny.controller.filterbeans.CopyFilterBean;
import com.bravo.johny.dto.Copy;

import java.util.List;

public interface CopyService {

    Copy addNewCopy(int bookId);

    List<Copy> getCopies(CopyFilterBean copyBean);

    Copy getCopyDetails(int copyId);

    Copy updateCopyDetails(Copy copy);

    void deleteCopyFromDatabase(int copyId);
}
