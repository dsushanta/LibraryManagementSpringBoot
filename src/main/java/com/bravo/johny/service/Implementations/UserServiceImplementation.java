package com.bravo.johny.service.Implementations;

import com.bravo.johny.dto.Book;
import com.bravo.johny.dto.Role;
import com.bravo.johny.dto.User;
import com.bravo.johny.dto.UserRole;
import com.bravo.johny.entity.RoleEntity;
import com.bravo.johny.entity.UserEntity;
import com.bravo.johny.repository.BookLifeCycleRepository;
import com.bravo.johny.repository.RoleRepository;
import com.bravo.johny.repository.UserRepository;
import com.bravo.johny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bravo.johny.utils.CommonUtils.throwBadRequestRuntimeException;


@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BookLifeCycleRepository bookLifeCycleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        if(entity.isEmpty())
            throwBadRequestRuntimeException("No User found with username : "+userName);

        UserEntity userEntity = entity.get();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getRole().getRoleName()));

        return new org.springframework.security.core.userdetails.User(userName, userEntity.getPassword(), authorities);
    }

    @Override
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

    @Override
    public User addNewUser(User user) {

        nullFieldValueCheck(user);

        boolean usernameExists = checkIfUsernameExistsInDatabase(user.getUserName());
        if(usernameExists)
            throwBadRequestRuntimeException("Username : "+user.getUserName()+" already exists !!");

        boolean emailExists = checkIfEmailExistsInDatabase(user.getEmail());
        if(emailExists)
            throwBadRequestRuntimeException("Email : "+user.getEmail()+" is already registered !!");
        var roleEntity = roleRepository.findByRoleName(UserRole.USER.getRole());
        user.setRole(roleEntity);
        UserEntity userEntity = prepareuserEntityFromUserDTO(user);
        UserEntity newUserEntity = userRepository.save(userEntity);

        return prepareUserDTOFromUserEntity(newUserEntity);
    }

    @Override
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

    @Override
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

    @Override
    public User getUserDetails(String userName) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        if(entity.isEmpty())
            throwBadRequestRuntimeException("No user found with username : "+userName);

        return prepareUserDTOFromUserEntity(entity.get());
    }

    @Override
    public User updateUserDetails(String userName, User user) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        if(entity.isEmpty())
            throwBadRequestRuntimeException("No user found with username : "+userName);

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

    @Override
    public User updateUserRole(String userName, Role role) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        if(entity.isEmpty())
            throwBadRequestRuntimeException("No user found with username : "+userName);

        UserEntity userEntity = entity.get();
        RoleEntity roleEntity = roleRepository.findByRoleName(role.getRole());
        if(!userEntity.getRole().equals(roleEntity)) {
            userEntity.setRole(roleEntity);
            userEntity = userRepository.save(userEntity);
        }

        var user = prepareUserDTOFromUserEntity(userEntity);

        return user;
    }

    @Override
    public void deleteUser(String userName) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        if(entity.isEmpty())
            throwBadRequestRuntimeException("No user found with username : "+userName);

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

        return entity.isPresent();
    }

    private boolean checkIfEmailExistsInDatabase(String email) {

        Optional<UserEntity> entity = userRepository.findByEmail(email);

        return entity.isPresent();
    }

    private UserEntity prepareuserEntityFromUserDTO(User user) {

        return new UserEntity(
                user.getUserName(),
                passwordEncoder.encode(user.getPassword()),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getFavGenre(),
                user.getFine(),
                user.getBookCount(),
                user.getRole()
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
        user.setRole(userEntity.getRole());

        return user;
    }
}
