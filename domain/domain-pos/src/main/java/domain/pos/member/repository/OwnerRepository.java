package domain.pos.member.repository;

import java.util.Optional;

public interface OwnerRepository {
	Optional<Object> findById(Long ownerId);
}
