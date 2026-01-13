package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.out.UserRepositoryPort;
import com.esiea.integrationplatform.infrastructure.mapper.UserMapper;
import com.esiea.integrationplatform.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter qui implémente le port UserRepositoryPort
 * Fait le pont entre le domaine et l'infrastructure JPA
 */
@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return UserMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByRole(String role) {
        return jpaUserRepository.findByRole(role).stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByActif(Boolean actif) {
        return jpaUserRepository.findByActif(actif).stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public long count() {
        return jpaUserRepository.count();
    }

    @Override
    public long countByRole(String role) {
        return jpaUserRepository.countByRole(role);
    }
}
