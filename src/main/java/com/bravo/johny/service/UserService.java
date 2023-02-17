package com.bravo.johny.service;

import com.bravo.johny.dto.User;
import com.bravo.johny.entity.UserEntity;
import com.bravo.johny.exception.FieldValueRequiredException;
import com.bravo.johny.repository.BookLifeCycleRepository;
import com.bravo.johny.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bravo.johny.utils.CommonUtils.throwBadRequestRuntimeException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookLifeCycleRepository bookLifeCycleRepository;

    public boolean authenticateUser(User user) {
        /*String username = user.getUserName();
        String password = user.getPassword();
        if(username == null)
            throw new FieldValueRequiredException("Value for Username field is either empty or null !!");
        if(password == null)
            throw new FieldValueRequiredException("Value for Password field is either empty or null !!");*/

        // TODO - needs to be corrected
        return true;
    }

    public User addNewUser(User user) {

        nullFieldValueCheck(user);

        boolean usernameExists = checkIfUsernameExistsInDatabase(user.getUserName());
        if(usernameExists)
            throwBadRequestRuntimeException("Username : "+user.getUserName()+" already exists !!");

        boolean emailExists = checkIfEmailExistsInDatabase(user.getEmail());
        if(emailExists)
            throwBadRequestRuntimeException("Email : "+user.getEmail()+" is already registered !!");

        UserEntity userEntity = prepareuserEntityFromUserDTO(user);
        UserEntity newUserEntity = userRepository.save(userEntity);

        return prepareUserDTOFromUserEntity(newUserEntity);
    }

    public List<User> getAllUsers(int offset, int limit) {

        List<UserEntity> userEntities;
        if (offset >= 0 && limit > 0) {
            Pageable pageable = PageRequest.of(offset, limit);
            userEntities = (List<UserEntity>) userRepository.findAll(pageable);
        } else
            userEntities = userRepository.findAll();

        return userEntities
                .stream()
                .map(element -> prepareUserDTOFromUserEntity(element))
                .collect(Collectors.toList());
    }

    public List<User> getUsers(String lastName, int offset, int limit) {

        List<UserEntity> userEntities;
        if (offset >= 0 && limit > 0) {
            Pageable pageable = PageRequest.of(offset, limit);
            userEntities = userRepository.findByLastName(lastName, pageable);
        } else
            userEntities = userRepository.findByLastName(lastName);

        return userEntities
                .stream()
                .map(element -> prepareUserDTOFromUserEntity(element))
                .collect(Collectors.toList());
    }

    public User getUserDetails(String userName) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No userEntity found with username : "+userName);

        return prepareUserDTOFromUserEntity(entity.get());
    }

    public User updateUserDetails(String userName, User user) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No userEntity found with username : "+userName);

        UserEntity userEntity = entity.get();
        if(user.getFirstName() != null && !user.getFirstName().isEmpty())
            userEntity.setFirstName(user.getFirstName());
        if(user.getLastName() != null && !user.getLastName().isEmpty())
            userEntity.setLastName(user.getLastName());
        if(user.getEmail() != null && !user.getEmail().isEmpty())
            userEntity.setEmail(user.getEmail());

        user = prepareUserDTOFromUserEntity(userRepository.save(userEntity));

        return user;
    }

    public void deleteUser(String userName) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No userEntity found with username : "+userName);

        bookLifeCycleRepository.deleteByUser(entity.get());
        userRepository.deleteByUserName(userName);
    }


    // ##################### PRIVATE METHODS ######################


    private void nullFieldValueCheck(User user) {

        if(user.getUserName() == null || user.getUserName().isEmpty())
            throwBadRequestRuntimeException("Value for Username field is either empty or null !!");
        if(user.getEmail() == null || user.getEmail().isEmpty())
            throwBadRequestRuntimeException("Value for Email field is either empty or null !!");
        if(user.getFirstName() == null || user.getFirstName().isEmpty())
            throwBadRequestRuntimeException("Value for First name field is either empty or null !!");
        if(user.getLastName() == null || user.getLastName().isEmpty())
            throwBadRequestRuntimeException("Value for Last name field is either empty or null !!");
    }

    private boolean checkIfUsernameExistsInDatabase(String userName) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        return entity.isPresent() ? true : false;
    }

    private boolean checkIfEmailExistsInDatabase(String email) {

        Optional<UserEntity> entity = userRepository.findByEmail(email);

        return entity.isPresent() ? true : false;
    }

    private UserEntity prepareuserEntityFromUserDTO(User user) {

        return new UserEntity(
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getFavGenre(),
                user.getFine(),
                user.getBookCount()
        );
    }

    private User prepareUserDTOFromUserEntity(UserEntity userEntity) {

        User user = new User();
        user.setUserName(userEntity.getUserName());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setFavGenre(userEntity.getFavGenre());
        user.setBookCount(userEntity.getBookCount());
        user.setFine(userEntity.getFine());

        return user;
    }
}
