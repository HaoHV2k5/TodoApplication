package vn.G3.TodoApplication.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import vn.G3.TodoApplication.dto.request.authentication.AuthenticationRequest;
import vn.G3.TodoApplication.dto.request.token.IntrospectRequest;
import vn.G3.TodoApplication.dto.response.AuthenticationResponse;
import vn.G3.TodoApplication.dto.response.token.IntrospectResponse;
import vn.G3.TodoApplication.entity.User;
import vn.G3.TodoApplication.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final String TOKEN_KEY = "YN05H5h/acK5nLZZnKfOx5ihHUhjmpxZvqxmQnfv0DplVwB5W+Kj2HCRufCG+80z";

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // can hoc ki hon
    public IntrospectResponse introspet(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier jwsVerifier = new MACVerifier(TOKEN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(jwsVerifier);
        return IntrospectResponse.builder()

                .valid(verified && expityTime.after(new Date()))
                .build();

    }

    public AuthenticationResponse checkLogin(AuthenticationRequest request) {
        var user = this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("username sai"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authented = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authented) {
            throw new RuntimeException("username hay mk khong khop");
        }
        var token = generationToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .isAuthented(authented)
                .build();

    }

    // can hoc ki hon
    private String generationToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("G3Group")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("CustomeClaim", "custom")

                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(TOKEN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return jwsObject.serialize();

    }
}
