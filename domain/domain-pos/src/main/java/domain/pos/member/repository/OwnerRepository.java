package domain.pos.member.repository;

import java.util.Optional;

import domain.pos.member.entity.Owner;

public interface OwnerRepository {
	Optional<Owner> findById(Long ownerId);
}
