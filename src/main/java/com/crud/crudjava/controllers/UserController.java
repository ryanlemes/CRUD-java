package com.crud.crudjava.controllers;


import com.crud.crudjava.Utils.Rsa;
import com.crud.crudjava.dto.KeyPairDto;
import com.crud.crudjava.dto.UserDetailDto;
import com.crud.crudjava.dto.UserDto;
import com.crud.crudjava.form.UserForm;
import com.crud.crudjava.models.KeyPair;
import com.crud.crudjava.models.User;
import com.crud.crudjava.repository.KeyPairRepository;
import com.crud.crudjava.repository.UserRepository;
import io.swagger.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final KeyPairRepository keyPairRepository;

    public UserController(UserRepository userRepository, KeyPairRepository keyPairRepository) {
        this.userRepository = userRepository;
        this.keyPairRepository = keyPairRepository;
    }

    /**
     * Return the list of registered users.
     * @return a User Dto with all registered users.
     */
    @GetMapping
    public List<UserDto> list() {
        // Find all users that the Deleted_at field is null.
        List<User> user = userRepository.findByDeletedAtIsNull();

        // Convert the users to Dto, making the Decrypt.
        return UserDto.convert(user, keyPairRepository);
    }

    /**
     * Return only one register of users, based on the user id.
     * @param id Long type User id
     * @return if exists a User Detail Dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> detail(@PathVariable Long id) {
        // Find only one users if the Deleted_at field is null and the id is the same as the passed on params.
        Optional<User> user = userRepository.findByIdAndDeletedAtIsNull(id);

        // Return some user if exists otherwise return notFound.
        return user
                .map(value -> ResponseEntity.ok(new UserDetailDto(keyPairRepository, value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Insert a new register on Users database.
     * @param form with the user's name and the user's email.
     * @param uriBuilder Header parameter passed to build the response.
     * @return a User Dto with the created user, if successed.
     */
    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> insert(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {
        try {
            // Create the RSA object to Create the pair of keys to encrypt the passed data.
            Rsa rsa = new Rsa();

            // Convert the user data performing encryption
            User user = form.converter(rsa);

            if (userRepository.existsByName(user.getName()))
                return ResponseEntity.badRequest().build();

            // If not exists some user that have the user name save the data
            userRepository.save(user);

            // Create a URI to send it to response header. To be used on something that consumes this API.
            URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

            // Save the KeyPair on database than return the user DTO.
            rsa.saveKeyPair(keyPairRepository, user.getId());

            return ResponseEntity.created(uri).body(new UserDto(keyPairRepository, user));
        }catch (Exception ex)
        {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update some user register.
     * @param id Long User id
     * @param form with the user's name and the user's email.
     * @return a User Dto with the updated date.
     */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserForm form) {
        // Find some user based on the long id.
        Optional<User> user = userRepository.findByIdAndDeletedAtIsNull(id);

        if(user.isEmpty())
            return ResponseEntity.notFound().build();

        try {
            // Create the RSA object to retrieve the pair of keys to encrypt the passed data.
            Rsa rsa = new Rsa();
            rsa = rsa.getKeyPair(keyPairRepository, id);

            // Fill the new user data.
            User userUpdated = form.converter(rsa);

            // Update the user data and set a new update at date.
            user.get().setName(userUpdated.getName());
            user.get().setEmail(userUpdated.getEmail());
            user.get().setUpdatedAt(LocalDateTime.now());

            return ResponseEntity.ok(new UserDto(keyPairRepository, user.get()));
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete some user.
     * @param id Long user id
     * @return a status if the operation was made
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable Long id) {
        // Search for some id based on the param.
        Optional<User> user = userRepository.findByIdAndDeletedAtIsNull(id);

        if(user.isEmpty())
            return ResponseEntity.notFound().build();

        // Is made a logic exclusion only setting a value to the property deleted_at.
        user.get().setDeletedAt(LocalDateTime.now());

        return ResponseEntity.status(204).build();
    }

    /**
     * Return the public key based on the user id passed.
     * @param id long user id
     * @return a key pair dto object with the public key, if exists.
     */
    @GetMapping("/publicKey/{id}")
    public ResponseEntity<KeyPairDto> getPublicKey(@PathVariable Long id){
        // Search for some id based on the param.
        Optional<User> user = userRepository.findByIdAndDeletedAtIsNull(id);

        if(user.isEmpty())
            return ResponseEntity.notFound().build();

        // Search for some keypair based on the id.
        Optional<KeyPair> keyPair = keyPairRepository.findByUserId(id);

        if(keyPair.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new KeyPairDto(keyPair.get()));
    }
}
