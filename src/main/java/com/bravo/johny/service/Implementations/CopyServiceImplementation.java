package com.bravo.johny.service.Implementations;

import com.bravo.johny.controller.filterbeans.CopyFilterBean;
import com.bravo.johny.dto.Copy;
import com.bravo.johny.entity.BookEntity;
import com.bravo.johny.entity.CopyEntity;
import com.bravo.johny.repository.BookLifeCycleRepository;
import com.bravo.johny.repository.BookRepository;
import com.bravo.johny.repository.CopyRepository;
import com.bravo.johny.service.CopyService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bravo.johny.utils.CommonUtils.throwBadRequestRuntimeException;


@Service
@NoArgsConstructor
public class CopyServiceImplementation implements CopyService {

    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookLifeCycleRepository bookLifeCycleRepository;

    @Override
    public Copy addNewCopy(int bookId) {

        Optional<BookEntity> entity = bookRepository.findByBookId(bookId);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No bookEntity found with bookId : "+bookId);

        CopyEntity copyEntity = new CopyEntity(entity.get());

        return prepareCopyDTOFromCopyEntity(copyRepository.save(copyEntity));
    }

    @Override
    public List<Copy> getCopies(CopyFilterBean copyBean) {

        List<CopyEntity> copyEntities;
        int bookId = copyBean.getBookId();
        int offset = copyBean.getOffset();
        int limit = copyBean.getLimit();

        Optional<BookEntity> entity = bookRepository.findByBookId(bookId);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No bookEntity found with bookId : "+bookId);

        BookEntity bookEntity = entity.get();

        if(offset >=0 && limit >0) {
            Pageable pageable = PageRequest.of(offset, limit);
            copyEntities = copyRepository.findByBook(bookEntity, pageable);
        } else {
            copyEntities = copyRepository.findByBook(bookEntity);
        }

        return copyEntities
                .stream()
                .map(copyEntity -> prepareCopyDTOFromCopyEntity(copyEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Copy getCopyDetails(int copyId) {

        Optional<CopyEntity> entity = copyRepository.findByCopyId(copyId);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No copy found with copy id : "+copyId);

        return prepareCopyDTOFromCopyEntity(entity.get());
    }

    @Override
    public Copy updateCopyDetails(Copy copy) {

        Optional<CopyEntity> entity = copyRepository.findByCopyId(copy.getCopyId());

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No copy found with copy id : "+copy.getCopyId());

        CopyEntity copyEntity = entity.get();
        copyEntity.setIssued(copy.isIssued());
        return prepareCopyDTOFromCopyEntity(copyRepository.save(copyEntity));
    }

    @Override
    public void deleteCopyFromDatabase(int copyId) {

        Optional<CopyEntity> entity = copyRepository.findByCopyId(copyId);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No copy found with copy id : "+copyId);
        else if(copyRepository.findByCopyIdAndIsIssued(copyId, true).isPresent())
            throwBadRequestRuntimeException("CopyEntity entry can not be removed as copy of the bookEntity has been issued to a customer !!");
        else {
            bookLifeCycleRepository.deleteByCopy(entity.get());
            copyRepository.deleteByCopyId(copyId);
        }
    }


    // ##################### PRIVATE METHODS ######################


    private Copy prepareCopyDTOFromCopyEntity(CopyEntity copyEntity) {

        Copy copy = new Copy();
        copy.setCopyId(copyEntity.getCopyId());
        copy.setBookId(copyEntity.getBook().getBookId());
        copy.setIssued(copyEntity.isIssued());
        //copy.setIssued(copyEntity.getIsIssued()==1 ? true : false);

        return copy;
    }
}
