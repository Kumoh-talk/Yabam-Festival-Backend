package domain.pos.member.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import domain.pos.member.entity.Owner;
import domain.pos.member.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OwnerReader {
	private final OwnerRepository ownerRepository;

	public Optional<Owner> findOwner(Long ownerId) {
		return ownerRepository.findById(ownerId);
	}
}
