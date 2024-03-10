package org.huba.users.service;

import lombok.RequiredArgsConstructor;
import org.huba.users.exception.BadRequestException;
import org.huba.users.model.Token;
import org.huba.users.model.TokenType;
import org.huba.users.model.User;
import org.huba.users.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public String generateToken(User user, TokenType type) {
        Token token = new Token();
        token.setValue(generateToken(type.isAlphabet(), type.getLength()));
        token.setUser(user);
        token.setType(type);
        token.setExpiredDate(new Date(System.currentTimeMillis() + type.getTimeToExp()));
        tokenRepository.save(token);
        return token.getValue();
    }

    public String generateToken(Boolean alphabet, int size) {
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        // create a super set of all characters
        String allCharacters = (alphabet?(alphabetsInLowerCase + alphabetsInUpperCase):"") + numbers;
        // initialize a string to hold result
        StringBuilder randomString = new StringBuilder();
        // loop for 10 times
        for (int i = 0; i < size; i++) {
            // generate a random number between 0 and length of all characters
            int randomIndex = (int)(Math.random() * allCharacters.length());
            // retrieve character at index and add it to result
            randomString.append(allCharacters.charAt(randomIndex));
        }
        return randomString.toString();
    }

    public void deleteToken(User user, TokenType type) {
        tokenRepository.deleteAll(tokenRepository.findAllByUserAndType(user, type));
    }

    public User getByToken(String token, TokenType type, Boolean delete) {
        Token tokenEntity = tokenRepository.findByValueAndAndType(token, type).orElseThrow(BadRequestException::new);
        if(tokenEntity.getExpiredDate().before(new Date(System.currentTimeMillis()))) {
            throw new BadRequestException();
        }
        User user = tokenEntity.getUser();
        if(delete) {
            tokenRepository.delete(tokenEntity);
        }
        return user;
    }
}
